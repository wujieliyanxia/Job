package com.buer.job.response;

import lombok.NoArgsConstructor;
import utils.Clock;

@NoArgsConstructor
public class BaseResponseStatus {
  public int code;
  public String details;
  public Long serverResponseTime;

  public BaseResponseStatus(int code, String details) {
    this.code = code;
    this.details = details;
    this.serverResponseTime= Clock.now();
  }
}
