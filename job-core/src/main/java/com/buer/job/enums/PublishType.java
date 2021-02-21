package com.buer.job.enums;

import com.buer.job.exception.JobException;

/**
 * Created by jiewu on 2021/2/20
 */
public enum PublishType {
  FIND_A_REPLACEMENT("A", "找替班"),
  PUBLISHED_BY_HR("B", "hr发布"),
  INTERNAL_RECOMMENDATION("C", "内推"),
  RESERVED("D", "有留用"),
  ;
  public String desc;
  public String code;

  PublishType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }


  public static PublishType fromCode(String code) {
    for (PublishType type : values()) {
      if (type.code.equals(code)) {
        return type;
      }
    }
    throw JobException.error("PublishType could not found. code = " + code);
  }
}
