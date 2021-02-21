package com.buer.job.enums;

import com.buer.job.exception.JobException;

/**
 * Created by jiewu on 2021/2/20
 */
public enum WorkType {
  AUTUMN_MOVE("A", "秋招"),
  PRACTICE("B", "实习"),
  ;
  public String desc;
  public String code;

  WorkType(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public static WorkType fromCode(String code) {
    for (WorkType type : values()) {
      if (type.code.equals(code)) {
        return type;
      }
    }
    throw JobException.error("WorkType could not found. code = " + code);
  }
}
