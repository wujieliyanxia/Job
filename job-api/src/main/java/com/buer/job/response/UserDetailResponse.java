package com.buer.job.response;

import com.buer.job.enums.Sex;
import com.buer.job.enums.Timer;
import com.buer.job.model.entity.UserAdditionalInfo;
import com.buer.job.utils.Clock;

import java.util.TimeZone;

public class UserDetailResponse {
  public String name;
  public String graduationTime;
  public String school;
  public String nickName;
  public Sex sex;
  public String headPic;

  public static UserDetailResponse from(UserAdditionalInfo userAdditionalInfo) {
    UserDetailResponse response = new UserDetailResponse();
    response.name = userAdditionalInfo.getName();
    response.graduationTime = Clock.dateTimeStrFromTimestampPlusDays(userAdditionalInfo.getGraduationTime(), Timer.YYYY_MM, TimeZone.getDefault(), 0);
    response.school = userAdditionalInfo.getSchool();
    response.nickName = userAdditionalInfo.getNickName();
    response.headPic = userAdditionalInfo.getHeaderPic();
    response.sex = Sex.fromCode(userAdditionalInfo.getSex());
    return response;
  }
}
