package com.buer.job.service.behavior;

import com.buer.job.enums.BehaviorSource;
import com.buer.job.enums.BehaviorType;
import com.buer.job.model.entity.UserBehavior;
import com.buer.job.model.mapper.UserBehaviorMapper;
import com.buer.job.utils.Clock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserBehaviorService {
  @Autowired
  private UserBehaviorMapper userBehaviorMapper;

  @Async
  public void asyncInsert(Long userId, Long targetId, BehaviorType behaviorType, BehaviorSource source) {
    insert(userId, targetId, behaviorType, source);
  }

  public void insert(Long userId, Long targetId, BehaviorType behaviorType, BehaviorSource source) {
    UserBehavior existBehavior = userBehaviorMapper.fetchByUniqueKey(userId, targetId, behaviorType.code, source.code);
    if (existBehavior == null) {
      UserBehavior userBehavior = new UserBehavior();
      userBehavior.setUserId(userId);
      userBehavior.setSource(source.code);
      userBehavior.setTargetId(targetId);
      userBehavior.setType(behaviorType.code);
      userBehavior.setTimeCreated(Clock.now());
      userBehaviorMapper.insert(userBehavior);
    } else {
      existBehavior.setTimeCreated(Clock.now());
      userBehaviorMapper.updateById(existBehavior);
    }

  }

  // TODO(WUJIE,未来加上分页吧),先限制100个
  public List<Long> fetchBehaviorList(Long userId, BehaviorType behaviorType, BehaviorSource source) {
    return userBehaviorMapper.fetchByParam(userId, behaviorType.code, source.code);
  }

  public boolean viewed(Long userId, Long targetId, BehaviorSource source) {
    return Objects.nonNull(userBehaviorMapper.fetchByUniqueKey(userId, targetId, BehaviorType.VIEW.code, source.code));
  }

  public Integer getCnt(Long targetId, BehaviorType behaviorType, BehaviorSource behaviorSource) {
    return userBehaviorMapper.fetchCnt(targetId, behaviorType.code, behaviorSource.code);
  }
}
