package com.buer.job.enums;

/**
 * Created by jiewu on 2021/1/26
 */
public enum JobCacheNameSpace {
  WE_CHAT_SESSION_KEY(1, "微信返回的sessionKey"),
  ;

  public int code;
  public String description;

  JobCacheNameSpace(int code, String description) {
    this.code = code;
    this.description = description;
  }
}
