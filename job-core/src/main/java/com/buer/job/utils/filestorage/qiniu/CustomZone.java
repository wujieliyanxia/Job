package com.buer.job.utils.filestorage.qiniu;

import com.buer.job.properties.QiniuConfig;
import com.qiniu.common.Zone;
import com.qiniu.common.ZoneReqInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhengxuming on 2017/2/8.
 */
@Component
public class CustomZone extends Zone {
  @Autowired
  private QiniuConfig qiniuConfig;

  public String getUpHttp(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.upHttp;
  }

  public String getUpBackupHttp(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.upBackupHttp;
  }

  public String getUpHttps(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.upHttps;
  }

  public String getUpHttps() {
    return qiniuConfig.uploadManager.upHttps;
  }

  public String getUpBackupHttps(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.upBackupHttps;
  }

  public String getUpBackupHttps() {
    return qiniuConfig.uploadManager.upBackupHttps;
  }

  public String getRsHttp(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.rsHttp;
  }

  public String getRsHttps(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.rsHttps;
  }

  public String getRsfHttp(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.rsfHttp;
  }

  public String getRsfHttps(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.rsfHttps;
  }

  public String getApiHttp(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.apiHttp;
  }

  public String getApiHttps(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.apiHttps;
  }

  public String getIovipHttp(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.iovipHttp;
  }

  public String getIovipHttps(ZoneReqInfo ab) {
    return qiniuConfig.uploadManager.iovipHttps;
  }
}
