package com.buer.job.enums;

public enum ArticleType {
  INTERVIEW_QUESTIONS("面试题"),
  INTERVIEW_EXPERIENCE("面经"),
  GOOD_ARTICLE("好文"),
  ;

  public String desc;

  ArticleType(String desc) {
    this.desc = desc;
  }
}
