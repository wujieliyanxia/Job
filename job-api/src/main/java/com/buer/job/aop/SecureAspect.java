package com.buer.job.aop;

import com.buer.job.controller.BaseController;
import com.buer.job.service.user.UserTokenService;
import com.buer.job.utils.RequestParser;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jpdu on 2018/12/17.
 */
@Aspect
@Component
public class SecureAspect {
  @Autowired
  private UserTokenService userTokenService;


  @Before("@annotation(com.buer.job.aop.JobSecuredApi)")
  public void verifyToken() {
    HttpServletRequest request = BaseController.request();
    verifyUserTokenPrivilege(request);
  }

  private void verifyUserTokenPrivilege(HttpServletRequest request) {
    String token = RequestParser.getUserToken(request);
    userTokenService.verifyToken(token);
  }
}
