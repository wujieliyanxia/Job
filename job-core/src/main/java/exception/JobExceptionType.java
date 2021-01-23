package exception;

import static exception.ExceptionLogLevel.ERROR;

/**
 * Created by jiewu on 2021/1/23
 */
public enum JobExceptionType {
  COMMON_SERVER_ERROR(1001, ERROR),
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
