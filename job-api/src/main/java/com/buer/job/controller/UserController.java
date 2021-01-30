package com.buer.job.controller;

import com.buer.job.aop.JobSecuredApi;
import com.buer.job.request.UploadWeChatMobileInfo;
import com.buer.job.response.LoginResponse;
import com.buer.job.response.Result;
import com.buer.job.service.user.UserService;
import com.buer.job.service.user.UserTokenService;
import com.buer.job.utils.RequestParser;
import com.buer.job.utils.ResponseUtil;
import com.buer.job.viewer.ViewerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by jiewu on 2021/1/28
 */
@RestController
public class UserController extends BaseController {
  @Autowired
  private UserService userService;
  @Autowired
  private UserTokenService userTokenService;

  @PostMapping("/api/login")
  public Result login(@RequestParam("code") String code) {
    String token = userService.loginWithWeChat(code);
    return ResponseUtil.originOk(LoginResponse.from(token));
  }

  @PostMapping("/api/logout")
  @JobSecuredApi
  public Result logout() {
    String token = RequestParser.getUserToken(request());
    userTokenService.disableToken(token);
    return ResponseUtil.originOk();
  }

  @PostMapping("/api/user")
  @JobSecuredApi
  public Result upload(@RequestBody @Valid UploadWeChatMobileInfo mobileInfo) {
    ViewerContext viewerContext = getViewerContext();
    userService.updateMobileNumber(viewerContext.userId, mobileInfo.encryptedData, mobileInfo.iv);
    return ResponseUtil.originOk();
  }

  // TODO(JIEWU,T0000)之后将这个test删除
  // TODO(JIEWU,T0000)添加全局的异常控制
  @GetMapping("/api/test")
  @JobSecuredApi
  public Result test() {
    return ResponseUtil.originOk();
  }


}
