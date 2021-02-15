package com.buer.job.response;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by jiewu on 2021/2/15
 */
public class ArticleSimpleListResponse {
  public List<ArticleSimpleResponse> responseList;
  public Integer size;

  public static ArticleSimpleListResponse from(List<ArticleSimpleResponse> responseList) {
    ArticleSimpleListResponse response = new ArticleSimpleListResponse();
    response.responseList = responseList;
    response.size = CollectionUtils.isEmpty(responseList) ? 0 : responseList.size();
    return response;
  }
}
