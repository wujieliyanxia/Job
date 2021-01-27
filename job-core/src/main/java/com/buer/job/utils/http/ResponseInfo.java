package com.buer.job.utils.http;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseInfo {
  public int httpCode;
  public String body;
  public byte[] bytes;

  public ResponseInfo(int httpCode, String body) {
    this.httpCode = httpCode;
    this.body = body;
  }

  public ResponseInfo(int httpCode, byte[] bytes) {
    this.httpCode = httpCode;
    this.bytes = bytes;
  }

  @Override
  public String toString() {
    return "ResponseInfo{" +
        "httpCode=" + httpCode +
        ", body='" + body + '\'' +
        '}';
  }

  public boolean isSuccess() {
    return this.httpCode >= 200 && this.httpCode < 300;
  }
}
