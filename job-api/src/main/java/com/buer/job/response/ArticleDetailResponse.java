package com.buer.job.response;

import com.buer.job.enums.ArticleType;
import com.buer.job.vo.ArticleDetailVO;
import lombok.Data;

/**
 * Created by jiewu on 2021/2/15
 */
@Data
public class ArticleDetailResponse {
  private Long id;
  private Long authorId;
  private String title;
  private ArticleType articleType;
  private String articleSource;
  private String creationType;
  private String htmlContent;
  private Long timePublished;

  public static ArticleDetailResponse from(ArticleDetailVO detailVO) {
    ArticleDetailResponse response = new ArticleDetailResponse();
    response.setId(detailVO.getId());
    response.setAuthorId(detailVO.getAuthorId());
    response.setTitle(detailVO.getTitle());
    response.setArticleType(detailVO.getArticleType());
    response.setArticleSource(detailVO.getArticleSource());
    response.setCreationType(detailVO.getCreationType());
    response.setTimePublished(detailVO.getTimePublished());
    response.setHtmlContent(detailVO.getHtmlContent());
    return response;
  }
}
