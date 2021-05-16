package com.buer.job.controller;

import com.buer.job.aop.JobSecuredApi;
import com.buer.job.enums.WorkType;
import com.buer.job.request.enums.UserBehaviorType;
import com.buer.job.response.*;
import com.buer.job.service.company.CompanyService;
import com.buer.job.service.job.JobService;
import com.buer.job.service.user.UserService;
import com.buer.job.utils.ResponseUtil;
import com.buer.job.utils.filestorage.IFileStorage;
import com.buer.job.viewer.ViewerContext;
import com.buer.job.vo.ArticleSimpleVO;
import com.buer.job.vo.CompanyVO;
import com.buer.job.vo.JobDetailVO;
import com.buer.job.vo.JobSimpleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jiewu on 2021/2/20
 */
@RestController
public class JobController extends BaseController {
  @Autowired
  private JobService jobService;
  @Autowired
  private CompanyService companyService;
  @Autowired
  private IFileStorage fileStorage;
  @Autowired
  private UserService userService;


  @GetMapping("/api/job")
  @JobSecuredApi
  public Result fetchJobList(@RequestParam(required = false, name = "endTime") Long endTime,
                             @RequestParam(name = "workType") WorkType workType) {
    List<JobSimpleVO> simpleVOS = jobService.getListVO(workType, endTime, 30);
    List<JobSimpleResponse> responseList = new ArrayList<>();
    for (JobSimpleVO simpleVO : simpleVOS) {
      CompanyVO companyVO = companyService.findByIdOrThrow(simpleVO.getCompanyId());
      String companyLogoUrl = fileStorage.getFileDownloadUrl(companyVO.getLogoKey());
      responseList.add(JobSimpleResponse.from(simpleVO, companyVO, companyLogoUrl));
    }
    return ResponseUtil.originOk(JobSimpleListResponse.from(responseList));
  }

  @GetMapping("/api/job/{id}")
  public Result detail(@PathVariable(name = "id") Long id) {
    JobDetailVO detailVO = jobService.getJobDetail(id);
    CompanyVO companyVO = companyService.findByIdOrThrow(detailVO.getSimpleVO().getCompanyId());
    String companyLogoUrl = fileStorage.getFileDownloadUrl(companyVO.getLogoKey());

    JobSimpleResponse simpleResponse = JobSimpleResponse.from(detailVO.getSimpleVO(), companyVO, companyLogoUrl);
    return ResponseUtil.originOk(JobDetailResponse.from(detailVO, companyVO, simpleResponse));
  }

//  /**
//   * 个人中心相关detail 岗位tab
//   * @param behaviorType
//   * @return
//   */
//  @GetMapping("/api/user/behavior/job")
//  @JobSecuredApi
//  public Result fetchArticleListWithUserBehavior(@RequestParam(name = "behaviorType") UserBehaviorType behaviorType) {
//    ViewerContext viewerContext = getViewerContext();
//    List<ArticleSimpleVO> articleSimpleVOList = jobService.getListVOWithUserBehavior(viewerContext.userId, behaviorType.getAssociatedArticleCntType());
//    List<ArticleSimpleResponse> responseList = articleSimpleVOList.stream().map(simpleVO -> from(simpleVO, viewerContext.userId)).collect(Collectors.toList());
//    return ResponseUtil.originOk(ArticleSimpleListResponse.from(responseList));
//  }

}
