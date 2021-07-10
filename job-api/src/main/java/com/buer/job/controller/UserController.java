package com.buer.job.controller;

import com.buer.job.aop.JobSecuredApi;
import com.buer.job.model.entity.UserAdditionalInfo;
import com.buer.job.request.UploadUserDetailRequest;
import com.buer.job.request.UploadWeChatMobileInfo;
import com.buer.job.response.LoginResponse;
import com.buer.job.response.Result;
import com.buer.job.response.UserDetailResponse;
import com.buer.job.service.user.UserAdditionalInfoService;
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
  @Autowired
  private UserAdditionalInfoService additionalInfoService;

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

  @PostMapping("/api/user/detail")
  @JobSecuredApi
  public Result uploadUserDetail(@RequestBody @Valid UploadUserDetailRequest detailRequest) {
    ViewerContext viewerContext = getViewerContext();
    additionalInfoService.update(viewerContext.userId, detailRequest.school, detailRequest.graduationTime, detailRequest.name, null, null, null);
    return ResponseUtil.originOk();
  }

  @GetMapping("/api/user/detail")
  @JobSecuredApi
  public Result getUserDetail() {
    ViewerContext viewerContext = getViewerContext();
    UserAdditionalInfo userAdditionalInfo = additionalInfoService.findByUserId(viewerContext.userId);
    return ResponseUtil.originOk(UserDetailResponse.from(userAdditionalInfo));
  }
}
