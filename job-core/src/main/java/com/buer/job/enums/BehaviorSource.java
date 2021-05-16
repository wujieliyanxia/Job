package com.buer.job.enums;

public enum BehaviorSource {
  ARTICLE("A", "面经"),
  JOB("B", "岗位"),
  ;

  public String code;
  public String desc;

  BehaviorSource(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
