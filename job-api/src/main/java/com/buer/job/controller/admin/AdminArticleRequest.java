package com.buer.job.controller.admin;

import com.buer.job.enums.ArticleType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by jiewu on 2021/2/21
 */
public class AdminArticleRequest extends AdminBaseRequest {
  @NotNull
  public Long authorId;
  @NotBlank
  public String title;
  @NotNull
  public ArticleType articleType;
  @NotBlank
  public String articleSource;
  @NotBlank
  public String creationType;
  @NotBlank
  public String introduction;
  @NotBlank
  public String content;
  @NotNull
  public String titlePageKey;
}
