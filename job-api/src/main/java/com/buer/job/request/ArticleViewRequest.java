package com.buer.job.request;

import com.buer.job.enums.ArticleCntType;

import javax.validation.constraints.NotNull;

/**
 * Created by jiewu on 2021/2/8
 */
public class ArticleViewRequest {
  @NotNull
  public Long articleId;
  @NotNull
  public Long userId;
  @NotNull
  public ArticleCntType articleCntType;
}
