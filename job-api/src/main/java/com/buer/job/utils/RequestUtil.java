package com.buer.job.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
  private static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";

  public static String getRemoteIp(HttpServletRequest request) {
    String ip = request.getHeader(HEADER_X_FORWARDED_FOR);
    if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
      return ip;
    }

    ip = request.getRemoteAddr();
    if (StringUtils.isBlank(ip)) {
      return "";
    }
    return ip;
  }
}
