package com.buer.job.global;

import com.buer.job.exception.ExceptionLogLevel;
import com.buer.job.exception.JobException;
import com.buer.job.response.Result;
import com.buer.job.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalRestResponseHandler implements ResponseBodyAdvice {

  @ResponseStatus(code = HttpStatus.OK)
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public Object defaultErrorHandler(HttpServletRequest request, Exception originalException) {
    JobException exception = JobException.wrap(originalException);
    logApiException(request, exception);
    return ResponseUtil.originServiceException(exception);
  }

  private void logApiException(HttpServletRequest request, JobException wrapException) {
    String formatMsg = String.format("[Caught exception] <%s %s> {%s %s %s}",
        request.getMethod(),
        request.getRequestURI(),
        wrapException.exceptionType.code,
        wrapException.exceptionType.name(),
        wrapException.detail
    );
    if (wrapException.exceptionType.logLevel == ExceptionLogLevel.ERROR) {
      log.error(formatMsg, wrapException);
    } else {
      log.warn(formatMsg, wrapException);
    }
  }

  @Override
  public boolean supports(MethodParameter returnType, Class converterType) {
    return (returnType.hasMethodAnnotation(ResponseBody.class)
        || Objects.requireNonNull(returnType.getMethod()).getDeclaringClass().isAnnotationPresent(RestController.class));
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
    // 方法自行构造了Result时, 直接返回
    if (body instanceof Result) {
      return body;
    }
    if (body instanceof InputStreamSource) {
      return body;
    }
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    return ResponseUtil.originOk(body);
  }
}

