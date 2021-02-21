package com.buer.job.service.job;

import com.buer.job.enums.PublishType;
import com.buer.job.enums.SalaryType;
import com.buer.job.enums.WorkType;
import com.buer.job.exception.JobException;
import com.buer.job.model.entity.Job;
import com.buer.job.model.mapper.JobMapper;
import com.buer.job.service.company.CompanyService;
import com.buer.job.utils.Clock;
import com.buer.job.utils.JsonUtils;
import com.buer.job.vo.JobDetailVO;
import com.buer.job.vo.JobSimpleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jiewu on 2021/2/20
 */
@Service
public class JobService {
  @Autowired
  private JobMapper jobMapper;
  @Autowired
  private CompanyService companyService;

  public void insert(Long companyId, WorkType workType,
                     String jobName, String workCity, String jobType,
                     PublishType publishType, Long publishTime,
                     Integer minSalary, Integer maxSalary, SalaryType salaryType,
                     String jobIntroduction, String jobRequirements, List<String> tags, String email) {
    Job job = new Job();
    job.setCompanyId(companyId);
    job.setWorkType(workType.code);
    job.setJobName(jobName);
    job.setWorkCity(workCity);
    job.setJobType(jobType);
    job.setPublishType(publishType.code);
    job.setPublishTime(publishTime);
    if (salaryType != SalaryType.NEGOTIABLE) {
      // 面议
      job.setMinSalary(minSalary);
      job.setMaxSalary(maxSalary);
    }
    job.setSalaryType(salaryType.code);
    job.setJobIntroduction(jobIntroduction);
    job.setJobRequirements(jobRequirements);
    job.setJobRequirementTags(JsonUtils.toString(tags));
    job.setContactEmail(email);
    Long now = Clock.now();
    job.setTimeCreated(now);
    job.setTimeUpdated(now);
    jobMapper.insert(job);
  }

  public List<JobSimpleVO> getListVO(WorkType workType, Long endTime, Integer limit) {
    List<Job> jobList = endTime == null ? jobMapper.find(workType.code, limit) : jobMapper.findByTimePublishAndWorkType(workType.code, endTime, limit);
    if (CollectionUtils.isEmpty(jobList)) {
      return new ArrayList<>();
    }
    return jobList.stream().map(JobSimpleVO::from).collect(Collectors.toList());
  }

  public JobDetailVO getJobDetail(Long id) {
    Job job = findByIdOrThrow(id);
    return JobDetailVO.from(job);
  }

  public Job findByIdOrThrow(Long id) {
    Job job = jobMapper.selectById(id);
    return Optional.ofNullable(job).orElseThrow(() -> JobException.error("can not find article by id,id is {}", id)
    );
  }
}
