package com.buer.job.controller;

import com.buer.job.aop.JobSecuredApi;
import com.buer.job.enums.ArticleCntType;
import com.buer.job.enums.ArticleType;
import com.buer.job.request.ArticleViewRequest;
import com.buer.job.request.enums.UserBehaviorType;
import com.buer.job.response.ArticleDetailResponse;
import com.buer.job.response.ArticleSimpleListResponse;
import com.buer.job.response.ArticleSimpleResponse;
import com.buer.job.response.Result;
import com.buer.job.service.article.ArticleService;
import com.buer.job.service.author.AuthorService;
import com.buer.job.utils.ResponseUtil;
import com.buer.job.utils.filestorage.IFileStorage;
import com.buer.job.viewer.ViewerContext;
import com.buer.job.vo.ArticleDetailVO;
import com.buer.job.vo.ArticleSimpleVO;
import com.buer.job.vo.AuthorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

  @GetMapping("/api/article")
  @JobSecuredApi
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
    return ResponseUtil.originOk(ArticleDetailResponse.from(detailVO));
  }

  @GetMapping("/api/user/behavior/article")
  @JobSecuredApi
  public Result fetchArticleListWithUserBehavior(@RequestParam(name = "behaviorType") UserBehaviorType behaviorType) {
    ViewerContext viewerContext = getViewerContext();
    List<ArticleSimpleVO> articleSimpleVOList = articleService.getListVOWithUserBehavior(viewerContext.userId, behaviorType.getAssociatedArticleCntType());
    List<ArticleSimpleResponse> responseList = articleSimpleVOList.stream().map(simpleVO -> from(simpleVO, viewerContext.userId)).collect(Collectors.toList());
    return ResponseUtil.originOk(ArticleSimpleListResponse.from(responseList));
  }


  @PostMapping("/api/article/cnt")
  public Result view(@RequestBody @Valid ArticleViewRequest viewRequest) {
    articleService.updateCnt(viewRequest.articleId, viewRequest.userId, viewRequest.articleCntType);
    return ResponseUtil.originOk();
  }

  private ArticleSimpleResponse from(ArticleSimpleVO articleSimpleVO, Long userId) {
    AuthorVO authorVO = authorService.findByAuthorIdOrThrow(articleSimpleVO.getId());
    int likeNumber = articleService.getArticleCnt(articleSimpleVO.getId(), ArticleCntType.LIKE_CNT);
    int viewNumber = articleService.getArticleCnt(articleSimpleVO.getId(), ArticleCntType.VIEW_CNT);
    boolean viewed = articleService.userViewedArticle(articleSimpleVO.getId(), userId);
    String imageUrl = fileStorage.getFileDownloadUrl(articleSimpleVO.getImageKey());
    String authorHeadUrl = fileStorage.getFileDownloadUrl(authorVO.getHeadImageKey());
    return ArticleSimpleResponse.from(articleSimpleVO, authorVO, imageUrl, authorHeadUrl, likeNumber, viewNumber, viewed);
  }
}
