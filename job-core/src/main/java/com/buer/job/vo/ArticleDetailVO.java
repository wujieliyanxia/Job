package com.buer.job.vo;

import com.buer.job.enums.ArticleType;
import com.buer.job.model.entity.Article;
import lombok.Data;

/**
 * Created by jiewu on 2021/2/6
 */
@Data
public class ArticleDetailVO {
  private Long id;
  private Long authorId;
  private String title;
  private ArticleType articleType;
  private String articleSource;
  private String creationType;
  private String htmlContent;
  private Long timePublished;

  public static ArticleDetailVO from(Article article, String htmlContent) {
    ArticleDetailVO detailVO = new ArticleDetailVO();
    detailVO.setId(article.getId());
    detailVO.setAuthorId(article.getAuthorId());
    detailVO.setTitle(article.getTitle());
    detailVO.setArticleType(ArticleType.valueOf(article.getArticleType()));
    detailVO.setArticleSource(article.getArticleSource());
    detailVO.setCreationType(article.getCreationType());
    detailVO.setTimePublished(article.getTimePublish());
    detailVO.setHtmlContent(htmlContent);
    return detailVO;
  }


}
