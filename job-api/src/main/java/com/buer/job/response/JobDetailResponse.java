package com.buer.job.response;

import com.buer.job.enums.WorkType;
import com.buer.job.vo.CompanyVO;
import com.buer.job.vo.JobDetailVO;

import java.util.List;

/**
 * Created by jiewu on 2021/2/21
 */
public class JobDetailResponse {
  public Long companyId;
  public String companyName;
  public String companyLogoUrl;
  public String companyProfile;
  public String jobName;
  public WorkType workType;
  public String jobType;
  public String publishType;
  public Long publishTime;
  public String workCity;
  public String salary;

  public String jobRequirements;
  public String jobIntroduction;
  public String contactEmail;
  public List<String> tagList;
  public String resumeName;


  public static JobDetailResponse from(JobDetailVO detailVO,
                                       CompanyVO companyVO,
                                       JobSimpleResponse simpleResponse) {
    JobDetailResponse response = new JobDetailResponse();
    response.companyId = simpleResponse.companyId;
    response.companyName = simpleResponse.companyName;
    response.companyLogoUrl = simpleResponse.companyLogoUrl;
    response.companyProfile = companyVO.getProfile();
    response.jobName = simpleResponse.jobName;
    response.workType = simpleResponse.workType;
    response.jobType = simpleResponse.jobType;
    response.publishType = simpleResponse.publishType;
    response.publishTime = simpleResponse.publishTime;
    response.workCity = simpleResponse.workCity;
    response.salary = simpleResponse.salary;

    response.jobRequirements = detailVO.getJobRequirements();
    response.jobIntroduction = detailVO.getJobIntroduction();
    response.contactEmail = detailVO.getContactEmail();
    response.tagList = detailVO.getTagList();
    return response;
  }
}
