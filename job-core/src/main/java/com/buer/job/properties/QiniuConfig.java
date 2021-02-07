package com.buer.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by jiewu on 2021/1/22
 */
@Data
@Component
@ConfigurationProperties("qiniu")
public class QiniuConfig {
  public PandaSecret pandaSecret;
  public UploadManager uploadManager;
  public String accessKey;
  public String secretKey;
  public int uploadResponseTimeout = 60;
  public int uploadWriteTimeout = 60;
  public int uploadRetryMaxTimes = 1;


  @Data
  public static class PandaSecret {
    public String bucket;
    public String url;
  }

  @Data
  public static class UploadManager {
    public String zone;
    public String upHttp;
    public String upBackupHttp;
    public String upBackupHttps;
    public String upHttps;
    public String rsHttp;
    public String rsHttps;
    public String rsfHttp;
    public String rsfHttps;
    public String apiHttp;
    public String apiHttps;
    public String iovipHttp;
    public String iovipHttps;
  }
}
