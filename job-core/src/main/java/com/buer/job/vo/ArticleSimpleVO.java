package com.buer.job.vo;

import com.buer.job.enums.ArticleType;
import com.buer.job.model.entity.Article;
import lombok.Data;

/**
 * Created by jiewu on 2021/2/6
 */
@Data
public class ArticleSimpleVO {
  private Long id;
  private Long authorId;
  private String title;
  private ArticleType articleType;
  private String introduction;
  private String imageKey;
  private long timeCreated;

  public static ArticleSimpleVO from(Article article) {
    ArticleSimpleVO simpleVO = new ArticleSimpleVO();
    simpleVO.setId(article.getId());
    simpleVO.setAuthorId(article.getAuthorId());
    simpleVO.setTitle(article.getTitle());
    simpleVO.setArticleType(ArticleType.valueOf(article.getArticleType()));
    simpleVO.setIntroduction(article.getIntroduction());
    simpleVO.setImageKey(article.getArticleImageKey());
    simpleVO.setTimeCreated(article.getTimeCreated());
    return simpleVO;
  }
}
