package com.buer.job.response;

import com.buer.job.enums.ArticleType;
import com.buer.job.vo.ArticleSimpleVO;
import com.buer.job.vo.AuthorVO;
import lombok.Data;

@Data
public class ArticleSimpleResponse {
  private Long id;
  private String title;
  private ArticleType articleType;
  private String introduction;
  private String coverImageUrl;
  private String authorHeaderUrl;
  private String authorName;
  private int likeNumber;
  private int numberOfView;
  private boolean viewed;
  private long timeCreated;

  public static ArticleSimpleResponse from(ArticleSimpleVO articleSimpleVO,
                                           AuthorVO authorVO,
                                           String coverImageUrl,
                                           String authorHeadUrl,
                                           int likeNumber,
                                           int numberOfView,
                                           boolean viewed) {
    ArticleSimpleResponse response = new ArticleSimpleResponse();
    response.setId(articleSimpleVO.getId());
    response.setTitle(articleSimpleVO.getTitle());
    response.setArticleType(articleSimpleVO.getArticleType());
    response.setCoverImageUrl(coverImageUrl);
    response.setAuthorHeaderUrl(authorHeadUrl);
    response.setAuthorName(authorVO.getName());
    response.setLikeNumber(likeNumber);
    response.setNumberOfView(numberOfView);
    response.setViewed(viewed);
    response.setTimeCreated(articleSimpleVO.getTimeCreated());
    return response;
  }
}
