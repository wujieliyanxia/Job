package com.buer.job.service.user;

import com.buer.job.enums.Sex;
import com.buer.job.enums.Timer;
import com.buer.job.exception.JobException;
import com.buer.job.model.entity.UserAdditionalInfo;
import com.buer.job.model.mapper.UserAdditionalInfoMapper;
import com.buer.job.utils.Clock;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@Service
public class UserAdditionalInfoService {
  @Autowired
  private UserAdditionalInfoMapper userAdditionalInfoMapper;

  public void init(Long userId) {
    UserAdditionalInfo mapperByUserId = userAdditionalInfoMapper.findByUserId(userId);
    if (mapperByUserId != null) {
      return;
    }
    UserAdditionalInfo userAdditionalInfo = new UserAdditionalInfo();
    userAdditionalInfo.setUserId(userId);
    long now = Clock.now();
    userAdditionalInfo.setTimeCreated(now);
    userAdditionalInfo.setTimeUpdated(now);
    userAdditionalInfoMapper.insert(userAdditionalInfo);
  }

  public void update(Long userId, String school, String graduationTime, Sex sex, String headerPic) {
    UserAdditionalInfo userAdditionalInfo = userAdditionalInfoMapper.findByUserId(userId);
    if (userAdditionalInfo == null) {
      throw JobException.error("can not update before init,userId is {}", userId);
    }
    userAdditionalInfo.setSchool(school);
    if (StringUtils.isNotBlank(graduationTime)) {
      userAdditionalInfo.setGraduationTime(Clock.dateStringToLong(graduationTime, Timer.YYYY_MM, TimeZone.getDefault()));
    }
    if (sex != null) {
      userAdditionalInfo.setSex(String.valueOf(sex.code));
    }
    if (StringUtils.isNotBlank(headerPic)) {
      userAdditionalInfo.setHeaderPic(headerPic);
    }
    long now = Clock.now();
    userAdditionalInfo.setTimeUpdated(now);
    userAdditionalInfoMapper.updateById(userAdditionalInfo);
  }
}
