package com.buer.job.service.behavior;

import com.buer.job.enums.BehaviorSource;
import com.buer.job.enums.BehaviorType;
import com.buer.job.model.entity.UserBehavior;
import com.buer.job.model.mapper.UserBehaviorMapper;
import com.buer.job.utils.Clock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserBehaviorService {
  @Autowired
  private UserBehaviorMapper userBehaviorMapper;

  public void insert(Long userId, Long targetId, BehaviorType behaviorType, BehaviorSource source) {
    UserBehavior userBehavior = new UserBehavior();
    userBehavior.setUserId(userId);
    userBehavior.setSource(source.code);
    userBehavior.setTargetId(targetId);
    userBehavior.setType(behaviorType.code);
    userBehavior.setTimeCreated(Clock.now());
    userBehaviorMapper.insert(userBehavior);
  }

  public List<Long> fetchBehaviorList(Long userId, BehaviorType behaviorType, BehaviorSource source) {
    return userBehaviorMapper.fetchByParam(userId, behaviorType.code, source.code);
  }
}
