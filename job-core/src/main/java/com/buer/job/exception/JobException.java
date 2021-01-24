package com.buer.job.exception;

import org.slf4j.helpers.MessageFormatter;


public class JobException extends RuntimeException {
  public JobExceptionType exceptionType;
  public String detail;

  private JobException(Throwable cause, JobExceptionType type, String usrMsg, String logMsg) {
    super(logMsg, cause);
    this.exceptionType = type;
    this.detail = usrMsg;
  }

  public static JobException error(String logMsg, Object... args) {
    return error(JobExceptionType.COMMON_SERVER_ERROR, logMsg, args);
  }

  public static JobException error(JobExceptionType type, String logMsg, Object... args) {
    Throwable cause = getCauseFromArgs(args);
    String formattedLogMsg = formatLogMsg(logMsg, args);
    return new JobException(cause, type, formattedLogMsg, formattedLogMsg);
  }

  public static JobException warn(JobExceptionType type, String usrMsg, String logMsg, Object... args) {
    Throwable cause = getCauseFromArgs(args);
    String formattedLogMsg = formatLogMsg(logMsg, args);
    return new JobException(cause, type, usrMsg, formattedLogMsg);
  }

  public static JobException warn(JobExceptionType type, String usrMsg, Throwable cause) {
    return new JobException(cause, type, usrMsg, usrMsg);
  }

  public static JobException warn(JobExceptionType type, String usrMsg) {
    return new JobException(null, type, usrMsg, usrMsg);
  }

  private static Throwable getCauseFromArgs(Object... args) {
    Object obj = args.length > 0 ? args[args.length - 1] : null;
    return (obj instanceof Exception) ? (Throwable) obj : null;
  }

  private static String formatLogMsg(String logMsg, Object... args) {
    return MessageFormatter.arrayFormat(logMsg, args).getMessage();
  }

  public static JobException wrap(Throwable e) {
    if (e instanceof JobException) {
      return (JobException) e;
    }
    return JobException.error("网络异常，请稍后再试", e);
  }
}
