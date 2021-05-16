package com.buer.job.request;

import com.buer.job.enums.BehaviorSource;
import com.buer.job.enums.BehaviorType;

import javax.validation.constraints.NotNull;

/**
 * Created by jiewu on 2021/2/8
 */
public class ArticleViewRequest {
  @NotNull
  public Long targetId;
  @NotNull
  public Long userId;
  @NotNull
  public BehaviorType behaviorType;
  @NotNull
  public BehaviorSource behaviorSource;
}
