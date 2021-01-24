package com.buer.job.response;

import com.buer.job.exception.JobException;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class BaseResponse<T> implements Result<T> {
  private static final transient int COMMON_SUCCESS_CODE = 0;

  public BaseResponseStatus status;
  public T body;

  public BaseResponse(HttpStatus status) {
    this(status, null);
  }

  public BaseResponse(BaseResponseStatus status) {
    this(status, null);
  }

  private BaseResponse(BaseResponseStatus status, T body) {
    this.status = status;
    this.body = body;
  }

  private BaseResponse(HttpStatus status, T body) {
    this.status = new BaseResponseStatus(status.value(), status.getReasonPhrase());
    this.body = body;
  }

  @Override
  public Result originOk() {
    return new BaseResponse<>(HttpStatus.OK);
  }

  @Override
  public Result originOk(T content) {
    return new BaseResponse<>(HttpStatus.OK, content);
  }

  @Override
  public Result originBadRequest(String message) {
    return new BaseResponse<>(new BaseResponseStatus(HttpStatus.BAD_REQUEST.value(), message));
  }

  @Override
  public Result originUnauthorized() {
    return new BaseResponse<>(HttpStatus.UNAUTHORIZED);
  }


  @Override
  public Result originNotFound() {
    return new BaseResponse<>(HttpStatus.NOT_FOUND, body);
  }

  @Override
  public Result serverException(JobException exception) {
    return new BaseResponse<>(new BaseResponseStatus(exception.exceptionType.code, exception.detail));
  }
}
