package com.buer.job.controller;

import com.buer.job.response.Result;
import com.buer.job.service.article.ArticleService;
import com.buer.job.vo.ArticleSimpleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jiewu on 2021/2/7
 */
@RestController
public class ArticleController extends BaseController {
  @Autowired
  private ArticleService articleService;

  @GetMapping("/api/article")
  public Result fetchArticleList(@RequestParam(required = false) Long endTime) {
    // 默认20吧
    List<ArticleSimpleVO> articleSimpleVOList = articleService.getListVO(endTime, 20);
    return null;
  }
}
