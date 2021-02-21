package com.buer.job.controller.admin;

import com.buer.job.enums.PublishType;
import com.buer.job.enums.SalaryType;
import com.buer.job.enums.WorkType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by jiewu on 2021/2/21
 */
public class AdminJobRequest extends AdminBaseRequest {
  @NotNull
  public Long companyId;
  @NotNull
  public WorkType workType;
  @NotBlank
  public String jobName;
  @NotBlank
  public String workCity;
  @NotBlank
  public String jobType;
  @NotNull
  public PublishType publishType;
  @NotNull
  public Long publishTime;
  public Integer minSalary;
  public Integer maxSalary;
  @NotNull
  public SalaryType salaryType;
  @NotBlank
  public String jobIntroduction;
  @NotBlank
  public String jobRequirements;
  public List<String> tags;
  @NotBlank
  public String email;
}
