package com.buer.job.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jiewu on 2021/1/28
 */
public class RequestParser {
  private static final String HEADER_USER_TOKEN = "USER-TOKEN";

  public static String getUserToken(HttpServletRequest request) {
    return request.getHeader(HEADER_USER_TOKEN);
  }
}
