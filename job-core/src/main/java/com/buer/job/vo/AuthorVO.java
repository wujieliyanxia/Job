package com.buer.job.vo;

import com.buer.job.model.entity.Author;

/**
 * Created by jiewu on 2021/2/6
 */
public class AuthorVO {
  public Long id;
  public String headImageKey;
  public String name;

  public static AuthorVO from(Author author) {
    AuthorVO authorVO = new AuthorVO();
    authorVO.id = author.getId();
    authorVO.headImageKey = author.getHeaderImageKey();
    return authorVO;
  }
}