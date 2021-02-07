package com.buer.job.utils.filestorage.qiniu;

public class Bucket {
  public String bucket;
  public String domain;
  public Long expire;

  public Bucket(String bucket, String domain) {
    this.bucket = bucket;
    this.domain = domain;
    this.expire = 900L;
  }
}
