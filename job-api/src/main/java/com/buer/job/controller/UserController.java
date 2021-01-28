package com.buer.job.controller;

import com.buer.job.aop.JobSecuredApi;
import com.buer.job.response.LoginResponse;
import com.buer.job.response.Result;
import com.buer.job.service.user.UserService;
import com.buer.job.utils.ResponseUtil;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jiewu on 2021/1/28
 */
@RestController
public class UserController extends BaseController {
  @Autowired
  private UserService userService;

  @PostMapping("/api/login")
  public Result login(@RequestParam("code") String code) {
    String token = userService.loginWithWeChat(code);
    return ResponseUtil.originOk(LoginResponse.from(token));
  }

  // TODO(JIEWU,T0000)之后将这个test删除
  @GetMapping("/api/test")
  @JobSecuredApi
  public Result test() {
    return ResponseUtil.originOk();
  }


}
