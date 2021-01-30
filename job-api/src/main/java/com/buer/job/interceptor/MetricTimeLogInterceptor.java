package com.buer.job.interceptor;

import com.buer.job.utils.MetricTimeLogUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MetricTimeLogInterceptor extends HandlerInterceptorAdapter {
  private static ThreadLocal<Long> timeBegin = new ThreadLocal<>();

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    timeBegin.set(System.currentTimeMillis());
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    Long cost = timeBegin.get();
    cost = cost == null ? null : System.currentTimeMillis() - cost;
    MetricTimeLogUtil.logNormal(request, cost);
    timeBegin.remove();
  }
}
