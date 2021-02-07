package com.buer.job.utils.filestorage;

import com.buer.job.exception.JobException;
import com.buer.job.exception.JobExceptionType;
import com.buer.job.properties.QiniuConfig;
import com.buer.job.utils.Clock;
import com.buer.job.utils.FileUtil;
import com.buer.job.utils.filestorage.qiniu.Bucket;
import com.buer.job.utils.filestorage.qiniu.CustomZone;
import com.buer.job.utils.filestorage.qiniu.QiniuUploadTokenVO;
import com.buer.job.utils.http.HttpClient;
import com.buer.job.utils.http.ResponseInfo;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.SocketException;
import java.net.SocketTimeoutException;

@Service
@Slf4j
public class QiniuFileStorageService implements IFileStorage {
  private UploadManager uploadManager;
  private volatile String uploadManagerZone;

  @Autowired
  private QiniuConfig qiniuConfig;
  @Autowired
  private CustomZone customZone;
  private static final long TOKEN_EXPIRE = 3600;

  private static final long DEFAULT_CONNECTION_TIMEOUT = 60L * 1000;
  private static final long DEFAULT_READ_TIMEOUT = 120L * 1000;

  private HttpClient httpClient = new HttpClient(DEFAULT_CONNECTION_TIMEOUT, DEFAULT_READ_TIMEOUT);

  private static Auth auth;

  @PostConstruct
  private void init() {
    auth = Auth.create(qiniuConfig.accessKey, qiniuConfig.secretKey);
  }

  public Bucket getBucket() {
    return new Bucket(
        qiniuConfig.pandaSecret.bucket,
        qiniuConfig.pandaSecret.url
    );
  }

  private UploadManager getUploadManager(String zone) {
    Configuration configuration;
    switch (zone) {
      case "customZone":
        configuration = new Configuration(customZone);
        break;
      case "zone0":
        configuration = new Configuration(Zone.zone0());
        break;
      case "zone1":
        configuration = new Configuration(Zone.zone1());
        break;
      case "zone2":
        configuration = new Configuration(Zone.zone2());
        break;
      case "zoneNa0":
        configuration = new Configuration(Zone.zoneNa0());
        break;
      case "as0":
        configuration = new Configuration(Zone.zoneAs0());
        break;
      default:
        configuration = new Configuration(Zone.autoZone());
    }
    configuration.readTimeout = qiniuConfig.uploadResponseTimeout;
    configuration.writeTimeout = qiniuConfig.uploadWriteTimeout;
    return new UploadManager(configuration);
  }

  private UploadManager getUploadManager() {
    String zone = qiniuConfig.uploadManager.zone;
    boolean zoneChanged = !StringUtils.equals(this.uploadManagerZone, zone);

    if (zoneChanged) {
      synchronized (this) {
        uploadManager = getUploadManager(zone);
        this.uploadManagerZone = zone;
      }
    }
    return uploadManager;
  }

  public static class UploadReturn {
    public long fsize;
    public String key;
    public String hash;
    public int width;
    public int height;
  }

  /**
   * @param bucketEnum
   * @param image      File or byte[]
   * @param key
   * @return
   */
  private String realUpload(QiniuBucketEnum bucketEnum, Object image, String key) {
    int maxRetryTimes = qiniuConfig.uploadRetryMaxTimes;
    for (int retryTimes = 1; retryTimes <= maxRetryTimes; retryTimes++) {
      try {
        return realUploadFile(bucketEnum, image, key);
      } catch (QiniuException e) {
        log.warn("QiniuFileStorageService#realUpload fail, upload times={}", retryTimes);
        Throwable throwable = e.getCause();
        if (!(throwable instanceof SocketTimeoutException || throwable instanceof SocketException)) {
          throw JobException.wrap(e);
        }
      } catch (Exception e) {
        throw JobException.wrap(e);
      }
    }
    log.warn("QiniuFileStorageService#realUpload fail,uploadRetryMaxTimes={}", maxRetryTimes);
    throw JobException.error("上传文件失败");
  }

  private String realUploadFile(QiniuBucketEnum bucketEnum, Object imageContent, String key) throws QiniuException {
    long timeBegin = Clock.now();
    Response res = null;
    try {
      if (imageContent instanceof byte[]) {
        res = getUploadManager().put((byte[]) imageContent, key, getUpToken(bucketEnum));
      } else if (imageContent instanceof File) {
        res = getUploadManager().put((File) imageContent, key, getUpToken(bucketEnum));
      } else {
        throw JobException.error("文件类型错误");
      }
      log.info("QiniuFileStorageService#realUploadFile, consume time={}", Clock.now() - timeBegin);
      UploadReturn ret = res.jsonToObject(UploadReturn.class);
      return ret.key;
    } catch (QiniuException e) {
      log.warn("QiniuFileStorageService#realUploadFile fail, consume time={}, bodyString={}",
          Clock.now() - timeBegin, res, e);
      throw e;
    }
  }

  private String getUpToken(QiniuBucketEnum bucketEnum) {
    Bucket qiniuBucket = getByQiniuBucketEnum(bucketEnum);
    return auth.uploadToken(qiniuBucket.bucket, null, TOKEN_EXPIRE, new StringMap()
        .putNotEmpty("returnBody", "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}"));
  }

  public QiniuUploadTokenVO getUploadToken() {
    return QiniuUploadTokenVO.from(
        getUpToken(QiniuBucketEnum.PANDA_SECRET), customZone.getUpHttps(), customZone.getUpBackupHttps());
  }

  //缩略图
  @Deprecated
  public String getDownloadUrl(QiniuBucketEnum bucketEnum, String key) {
    return getDownloadUrl(getByQiniuBucketEnum(bucketEnum), key);
  }

  //缩略图
  private String getDownloadUrl(Bucket qiniuBucket, String key) {
    if (StringUtils.isBlank(key)) {
      return null;
    }
    // 如果是正常的http链接, 直接返回
    if (StringUtils.startsWith(key, "http")) {
      return key;
    }
    String url = qiniuBucket.domain + key + "?imageView2/2/w/1024";
    return auth.privateDownloadUrl(url, qiniuBucket.expire);
  }

  @Override
  public String getFileDownloadUrl(String key) {
    Bucket qiniuBucket = getBucket();
    if (StringUtils.isBlank(key)) {
      return null;
    }
    // 如果是正常的http链接, 直接返回
    if (StringUtils.startsWith(key, "http")) {
      return key;
    }
    String url = qiniuBucket.domain + key;
    return auth.privateDownloadUrl(url, qiniuBucket.expire);
  }

  private Bucket getByQiniuBucketEnum(QiniuBucketEnum qiniuBucketEnum) {
    switch (qiniuBucketEnum) {
      case PANDA_SECRET:
        return getBucket();
      default:
        throw JobException.error("unknown QiniuBucketEnum bucketEnum=" + qiniuBucketEnum);
    }
  }

  @Override
  public byte[] downloadFileWithKeyOrThrowException(String key) {
    String url = getFileDownloadUrl(key);
    if (url == null) {
      throw JobException.error("Cann't get url with key:" + key);
    }
    Request request = new Request.Builder()
        .url(url)
        .build();
    ResponseInfo resp = httpClient.syncGetBytesOrThrow(request);
    byte[] bytes = resp.isSuccess()
        ? resp.bytes
        : null;
    if (bytes == null) {
      throw JobException.error("Cann't get Image with url:" + url);
    }

    return bytes;
  }

  @Override
  public String uploadFile(byte[] bytes, String filename, FileType fileType) {
    try {
      if (fileType == FileType.IMAGE) {
        bytes = FileUtil.resizeImage(bytes);
      }
    } catch (Exception e) {
      log.warn("文件处理失败!", e);
      throw JobException.warn(JobExceptionType.COMMON_CUSTOM_MESSAGE, "文件格式不正确");
    }
    try {
      return realUpload(QiniuBucketEnum.PANDA_SECRET, bytes, filename);
    } catch (Exception e) {
      throw JobException.warn(JobExceptionType.COMMON_CUSTOM_MESSAGE, "上传文件错误，请重试", e);
    }
  }
}
