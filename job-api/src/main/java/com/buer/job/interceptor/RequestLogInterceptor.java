package com.buer.job.interceptor;

import com.buer.job.utils.JsonUtils;
import com.buer.job.utils.RequestParser;
import com.buer.job.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jpdu on 2018/12/10.
 */
@Slf4j
@Component
public class RequestLogInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String servletPath = request.getServletPath();
    String ip = RequestUtil.getRemoteIp(request);
    String method = request.getMethod();
    String userAgent = request.getHeader("User-Agent");
    String userToken = RequestParser.getUserToken(request);
    String parameterMap = JsonUtils.toString(request.getParameterMap());

    log.info("[ecRequest start] {}", getRequestStrForLog(ip, method, servletPath, userAgent, userToken, parameterMap));

    return true;
  }

  private String getRequestStrForLog(
      String ip,
      String method,
      String servletPath,
      String userAgent,
      String userToken,
      String parameterMap
  ) {
    String requestStr = null;
    try {
      requestStr = String.format("%s %s %s %s [%s] %s", ip, method, servletPath, userAgent, userToken, parameterMap);
    } catch (Exception e) {
      log.warn("parse ecRequest info error", e);
    }
    return requestStr;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    log.info("[ecRequest end]");
  }
}
