package com.buer.job.controller;

import com.buer.job.aop.JobSecuredApi;
import com.buer.job.enums.ArticleType;
import com.buer.job.enums.BehaviorSource;
import com.buer.job.enums.BehaviorType;
import com.buer.job.response.ArticleDetailResponse;
import com.buer.job.response.ArticleSimpleListResponse;
import com.buer.job.response.ArticleSimpleResponse;
import com.buer.job.response.Result;
import com.buer.job.service.article.ArticleService;
import com.buer.job.service.author.AuthorService;
import com.buer.job.service.behavior.UserBehaviorService;
import com.buer.job.utils.ResponseUtil;
import com.buer.job.utils.filestorage.IFileStorage;
import com.buer.job.viewer.ViewerContext;
import com.buer.job.vo.ArticleDetailVO;
import com.buer.job.vo.ArticleSimpleVO;
import com.buer.job.vo.AuthorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jiewu on 2021/2/7
 */
@RestController
public class ArticleController extends BaseController {
  @Autowired
  private ArticleService articleService;
  @Autowired
  private AuthorService authorService;
  @Autowired
  private IFileStorage fileStorage;
  @Autowired
  private UserBehaviorService userBehaviorService;

  @GetMapping("/api/article")
  public Result fetchArticleList(@RequestParam(required = false, name = "endTime") Long endTime,
                                 @RequestParam(name = "articleType") ArticleType articleType) {
    ViewerContext viewerContext = getViewerContext();
    // 默认20吧
    List<ArticleSimpleVO> articleSimpleVOList = articleService.getListVO(articleType, endTime, 20);
    List<ArticleSimpleResponse> responseList = articleSimpleVOList.stream().map(simpleVO -> from(simpleVO, viewerContext.userId)).collect(Collectors.toList());
    return ResponseUtil.originOk(ArticleSimpleListResponse.from(responseList));
  }

  @GetMapping("/api/article/{id}")
  public Result detail(@PathVariable(name = "id") Long id) {
    ArticleDetailVO detailVO = articleService.getArticleDetail(id);
    ViewerContext viewerContext = getViewerContext();
    userBehaviorService.asyncInsert(viewerContext.userId, id, BehaviorType.VIEW, BehaviorSource.ARTICLE);
    return ResponseUtil.originOk(ArticleDetailResponse.from(detailVO));
  }

  /**
   * 个人中心相关detail
   *
   * @param behaviorType
   * @return
   */
  @GetMapping("/api/user/behavior/article")
  @JobSecuredApi
  public Result fetchArticleListWithUserBehavior(@RequestParam(name = "behaviorType") BehaviorType behaviorType) {
    ViewerContext viewerContext = getViewerContext();
    List<Long> targetIds = userBehaviorService.fetchBehaviorList(viewerContext.userId, behaviorType, BehaviorSource.ARTICLE);
    List<ArticleSimpleVO> articleSimpleVOList = articleService.getListVO(targetIds);
    List<ArticleSimpleResponse> responseList = articleSimpleVOList.stream().map(simpleVO -> from(simpleVO, viewerContext.userId)).collect(Collectors.toList());
    return ResponseUtil.originOk(ArticleSimpleListResponse.from(responseList));
  }

  private ArticleSimpleResponse from(ArticleSimpleVO articleSimpleVO, Long userId) {
    AuthorVO authorVO = authorService.findByAuthorIdOrThrow(articleSimpleVO.getId());
    int likeNumber = articleService.getArticleCnt(articleSimpleVO.getId(), BehaviorType.COLLECTED);
    int viewNumber = articleService.getArticleCnt(articleSimpleVO.getId(), BehaviorType.VIEW);
    boolean viewed = articleService.userViewedArticle(articleSimpleVO.getId(), userId);
    String imageUrl = fileStorage.getFileDownloadUrl(articleSimpleVO.getImageKey());
    String authorHeadUrl = fileStorage.getFileDownloadUrl(authorVO.getHeadImageKey());
    return ArticleSimpleResponse.from(articleSimpleVO, authorVO, imageUrl, authorHeadUrl, likeNumber, viewNumber, viewed);
  }
}
