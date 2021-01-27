package com.buer.job.enums;


import com.buer.job.exception.JobException;

public enum AvailabilityStatus {
  ENABLED("E"),
  DISABLED("D"),
  ;

  public String code;

  AvailabilityStatus(String code) {
    this.code = code;
  }

  public static AvailabilityStatus fromCode(String code) {
    for (AvailabilityStatus status : values()) {
      if (status.code.equals(code)) {
        return status;
      }
    }
    throw JobException.error("AvailabilityStatus could not found. code = " + code);
  }
}
