package com.buer.job.exception;


import static com.buer.job.exception.ExceptionLogLevel.ERROR;
import static com.buer.job.exception.ExceptionLogLevel.WARN;

/**
 * Created by jiewu on 2021/1/23
 */
public enum JobExceptionType {
  COMMON_SERVER_ERROR(1001, ERROR),
  SECURE_API_UNAUTHORIZED_USER(1002, WARN),
  ;

  public int code;
  public ExceptionLogLevel logLevel;

  JobExceptionType(int code, ExceptionLogLevel logLevel) {
    this.code = code;
    this.logLevel = logLevel;
  }


  @Override
  public String toString() {
    return "error_code: " + code + ", name: " + this.name();
  }
}
