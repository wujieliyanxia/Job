package com.buer.job.enums;

/**
 * Created by jiewu on 2021/1/26
 */
public enum JobCacheNameSpace {
  WE_CHAT_SESSION_KEY(1, "微信返回的sessionKey"),
  // 文章的相关信息
  ARTICLE_CNT_KEY(2, "文章的一些数据"),

  // 用户行为的相关信息
  ARTICLE_USER_VIEW_KEY(3, "用户文章的浏览记录"),
  ARTICLE_USER_LIKE_KEY(4, "用户的点赞记录"),
  ARTICLE_USER_FORWARD_KEY(5, "用户的转发记录"),
  ;

  public int code;
  public String description;

  JobCacheNameSpace(int code, String description) {
    this.code = code;
    this.description = description;
  }
}
