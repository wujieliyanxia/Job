package com.buer.job.request.enums;

import com.buer.job.enums.ArticleCntType;

/**
 * Created by jiewu on 2021/4/2
 */
public enum UserBehaviorType {
  VIEWED("浏览过的") {
    @Override
    public ArticleCntType getAssociatedArticleCntType() {
      return ArticleCntType.VIEW_CNT;
    }
  },
  COLLECTED("收藏过的") {
    @Override
    public ArticleCntType getAssociatedArticleCntType() {
      return ArticleCntType.LIKE_CNT;
    }
  },
  FORWARD("转发过的") {
    @Override
    public ArticleCntType getAssociatedArticleCntType() {
      return ArticleCntType.FORWARD_CNT;
    }
  },
  ;


  public String desc;

  UserBehaviorType(String desc) {
    this.desc = desc;
  }

  public abstract ArticleCntType getAssociatedArticleCntType();

}
