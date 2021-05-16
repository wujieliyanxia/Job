package com.buer.job.controller;

import com.buer.job.request.ArticleViewRequest;
import com.buer.job.response.Result;
import com.buer.job.service.behavior.UserBehaviorService;
import com.buer.job.utils.ResponseUtil;
import com.buer.job.viewer.ViewerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by jiewu on 2021/5/16
 */
@RestController
public class UserBehaviorController extends BaseController {
  @Autowired
  private UserBehaviorService userBehaviorService;


  @PostMapping("/api/user/behavior")
  public Result view(@RequestBody @Valid ArticleViewRequest viewRequest) {
    ViewerContext viewerContext = getViewerContext();
    userBehaviorService.insert(viewerContext.userId, viewRequest.targetId, viewRequest.behaviorType, viewRequest.behaviorSource);
    return ResponseUtil.originOk();
  }
}
