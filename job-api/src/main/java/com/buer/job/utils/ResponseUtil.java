package com.buer.job.utils;

import com.buer.job.response.BaseResponse;
import com.buer.job.response.Result;
import com.buer.job.exception.JobException;

public final class ResponseUtil {
  private static Result result = new BaseResponse();

  public static Result originBadRequest(String content) {
    return result.originBadRequest(content);
  }

  public static Result originUnauthorized() {
    return result.originUnauthorized();
  }

  public static Result originNotFound() {
    return result.originNotFound();
  }

  public static Result originServiceException(JobException exception) {
    return result.serverException(exception);
  }

  public static Result originOk() {
    return result.originOk();
  }

  public static Result originOk(Object object) {
    return result.originOk(object);
  }
}