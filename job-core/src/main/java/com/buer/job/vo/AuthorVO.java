package com.buer.job.vo;

import com.buer.job.model.entity.Author;
import lombok.Data;

/**
 * Created by jiewu on 2021/2/6
 */
@Data
public class AuthorVO {
  private Long id;
  private String headImageKey;
  private String name;

  public static AuthorVO from(Author author) {
    AuthorVO authorVO = new AuthorVO();
    authorVO.setId(author.getId());
    authorVO.setHeadImageKey(author.getHeaderImageKey());
    authorVO.setName(author.getName());
    return authorVO;
  }
}