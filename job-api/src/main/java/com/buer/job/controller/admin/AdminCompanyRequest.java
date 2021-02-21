package com.buer.job.controller.admin;

import javax.validation.constraints.NotBlank;

/**
 * Created by jiewu on 2021/2/21
 */
public class AdminCompanyRequest extends AdminBaseRequest {
  @NotBlank
  public String companyName;
  @NotBlank
  public String profile;
  @NotBlank
  public String address;
}
