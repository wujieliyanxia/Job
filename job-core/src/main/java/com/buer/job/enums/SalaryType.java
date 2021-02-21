package com.buer.job.enums;

import com.buer.job.exception.JobException;

/**
 * Created by jiewu on 2021/2/20
 */
public enum SalaryType {
  NEGOTIABLE("A", "面议"),
  DAY("B", "天"),
  WEEK("C", "周"),
  MONTH("D", "月"),
  YEAY("E", "年"),
  ;
  public String desc;
  public String code;

  SalaryType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public static SalaryType fromCode(String code) {
    for (SalaryType type : values()) {
      if (type.code.equals(code)) {
        return type;
      }
    }
    throw JobException.error("SalaryType could not found. code = " + code);
  }
}
