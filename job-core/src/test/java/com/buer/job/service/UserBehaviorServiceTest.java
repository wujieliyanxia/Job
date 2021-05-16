package com.buer.job.service;

import com.buer.job.BaseTest;
import com.buer.job.enums.BehaviorSource;
import com.buer.job.enums.BehaviorType;
import com.buer.job.service.behavior.UserBehaviorService;
import com.buer.job.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserBehaviorServiceTest extends BaseTest {
  @Autowired
  private UserBehaviorService userBehaviorService;

  @Test
  public void testInsert() {
    userBehaviorService.insert(1L, 2L, BehaviorType.VIEW, BehaviorSource.JOB);
    List<Long> behaviorList = userBehaviorService.fetchBehaviorList(1L, BehaviorType.VIEW, BehaviorSource.JOB);
    Assert.assertNotNull(behaviorList);
    Assert.assertNotNull(behaviorList.get(0));
    System.out.println(JsonUtils.toString(behaviorList.get(0)));
  }
}
