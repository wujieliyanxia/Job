package com.buer.job.vo;

import com.buer.job.model.entity.Job;
import com.buer.job.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;

import java.util.List;

/**
 * Created by jiewu on 2021/2/20
 */
@Data
public class JobDetailVO {
  private JobSimpleVO simpleVO;
  private String jobRequirements;
  private String jobIntroduction;
  private String contactEmail;
  private List<String> tagList;

  public static JobDetailVO from(Job job) {
    JobDetailVO jobDetailVO = new JobDetailVO();
    jobDetailVO.setSimpleVO(JobSimpleVO.from(job));
    jobDetailVO.setJobRequirements(job.getJobRequirements());
    jobDetailVO.setJobIntroduction(job.getJobIntroduction());
    jobDetailVO.setContactEmail(job.getContactEmail());
    jobDetailVO.setTagList(JsonUtils.fromOrException(job.getJobRequirementTags(), new TypeReference<List<String>>() {
    }));
    return jobDetailVO;
  }
}
