package com.buer.job.controller.admin;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by jiewu on 2021/2/21
 */
public class AdminAuthorRequest extends AdminBaseRequest {
  @NotBlank
  public String name;
  @NotNull
  public String authorImageKey;
}
