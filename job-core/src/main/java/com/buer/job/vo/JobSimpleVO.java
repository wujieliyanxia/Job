package com.buer.job.vo;

import com.buer.job.enums.PublishType;
import com.buer.job.enums.SalaryType;
import com.buer.job.enums.WorkType;
import com.buer.job.model.entity.Job;
import lombok.Data;

/**
 * Created by jiewu on 2021/2/20
 */
@Data
public class JobSimpleVO {
  private Long jobId;
  private Long companyId;
  private String jobName;
  private WorkType workType;
  private String jobType;
  private PublishType publishType;
  private Long publishTime;
  private String workCity;
  private SalaryType salaryType;
  private Integer minSalary;
  private Integer maxSalary;

  public static JobSimpleVO from(Job job) {
    JobSimpleVO jobSimpleVO = new JobSimpleVO();
    jobSimpleVO.setJobId(job.getId());
    jobSimpleVO.setCompanyId(job.getCompanyId());
    jobSimpleVO.setJobName(job.getJobName());
    jobSimpleVO.setWorkType(WorkType.fromCode(job.getWorkType()));
    jobSimpleVO.setPublishType(PublishType.fromCode(job.getPublishType()));
    jobSimpleVO.setPublishTime(job.getPublishTime());
    jobSimpleVO.setWorkCity(job.getWorkCity());
    jobSimpleVO.setSalaryType(SalaryType.fromCode(job.getSalaryType()));
    jobSimpleVO.setMinSalary(job.getMinSalary());
    jobSimpleVO.setMaxSalary(job.getMaxSalary());
    return jobSimpleVO;
  }
}
