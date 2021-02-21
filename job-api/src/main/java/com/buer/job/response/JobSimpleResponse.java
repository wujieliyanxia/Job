package com.buer.job.response;

import com.buer.job.enums.WorkType;
import com.buer.job.exception.JobException;
import com.buer.job.vo.CompanyVO;
import com.buer.job.vo.JobSimpleVO;

/**
 * Created by jiewu on 2021/2/21
 */
public class JobSimpleResponse {
  public Long companyId;
  public String companyName;
  public String companyLogoUrl;
  public String jobName;
  public WorkType workType;
  public String jobType;
  public String publishType;
  public Long publishTime;
  public String workCity;
  public String salary;

  public static JobSimpleResponse from(JobSimpleVO simpleVO, CompanyVO companyVO, String companyLogoUrl) {
    JobSimpleResponse response = new JobSimpleResponse();
    response.companyId = simpleVO.getCompanyId();
    response.companyName = companyVO.getCompanyName();
    response.companyLogoUrl = companyLogoUrl;
    response.jobName = simpleVO.getJobName();
    response.workType = simpleVO.getWorkType();
    response.jobType = simpleVO.getJobType();
    response.publishType = simpleVO.getPublishType().desc;
    response.publishTime = simpleVO.getPublishTime();
    response.workCity = simpleVO.getWorkCity();
    switch (simpleVO.getSalaryType()) {
      case NEGOTIABLE:
        response.salary = "面议";
        break;
      case DAY:
        response.salary = realSalary(simpleVO) + "/天";
        break;
      case WEEK:
        response.salary = realSalary(simpleVO) + "/周";
        break;
      case MONTH:
        response.salary = realSalary(simpleVO) + "/月";
        break;
      case YEAY:
        response.salary = realSalary(simpleVO) + "/年";
        break;
      default:
        throw JobException.error("can not find salaryType {}", simpleVO.getSalaryType());
    }
    return response;
  }

  private static String realSalary(JobSimpleVO simpleVO) {
    return simpleVO.getMaxSalary().equals(simpleVO.getMinSalary()) ? simpleVO.getMaxSalary().toString() : simpleVO.getMinSalary() + "-" + simpleVO.getMaxSalary();
  }
}
