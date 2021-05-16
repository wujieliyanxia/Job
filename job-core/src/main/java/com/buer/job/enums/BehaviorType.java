package com.buer.job.enums;

public enum BehaviorType {
  VIEW("V", "浏览"),
  FORWARD("F", "转发"),
  COLLECTED("", "收藏"),
  ;

  public String code;
  public String desc;

  BehaviorType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
