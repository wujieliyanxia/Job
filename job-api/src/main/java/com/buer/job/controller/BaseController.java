package com.buer.job.controller;

import com.buer.job.model.entity.UserToken;
import com.buer.job.service.user.UserTokenService;
import com.buer.job.utils.RequestParser;
import com.buer.job.viewer.ViewerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jiewu on 2021/1/28
 */
public class BaseController {
  @Autowired
  private UserTokenService userTokenService;

  public static HttpServletRequest request() {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return requestAttributes.getRequest();
  }

  public static HttpServletResponse response() {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return requestAttributes.getResponse();
  }

  protected ViewerContext getViewerContext() {
    String token = RequestParser.getUserToken(request());
    UserToken userToken = userTokenService.getByToken(token);
    ViewerContext viewerContext = new ViewerContext();
    viewerContext.userId = userToken.getUserId();
    return viewerContext;
  }
}
