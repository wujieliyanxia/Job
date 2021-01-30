package com.buer.job.service.user;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.buer.job.enums.AvailabilityStatus;
import com.buer.job.exception.JobException;
import com.buer.job.exception.JobExceptionType;
import com.buer.job.model.entity.UserToken;
import com.buer.job.model.mapper.UserTokenMapper;
import com.buer.job.utils.Clock;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jiewu on 2021/1/26
 */
@Service
public class UserTokenService {
  @Autowired
  private UserTokenMapper userTokenMapper;

  @Transactional
  public void insertAndInvalidateOldToken(Long userId, String token, Long timeExpired) {
    // invalid old token
    LambdaUpdateWrapper<UserToken> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    lambdaUpdateWrapper.eq(UserToken::getUserId, userId).set(UserToken::getStatus, AvailabilityStatus.DISABLED.code);
    userTokenMapper.update(null, lambdaUpdateWrapper);

    // insert new token
    UserToken userToken = new UserToken();
    Long now = Clock.now();
    userToken.setUserId(userId);
    userToken.setToken(token);
    userToken.setStatus(AvailabilityStatus.ENABLED.code);
    userToken.setTimeCreated(now);
    userToken.setTimeUpdated(now);
    userToken.setTimeExpired(timeExpired);
    userTokenMapper.insert(userToken);
  }

  public void verifyToken(String token) {
    if (StringUtils.isBlank(token)) {
      throw JobException.warn(JobExceptionType.SECURE_API_UNAUTHORIZED_USER, "请重新登录");
    }
    UserToken userToken = userTokenMapper.findValidByToken(token);
    if (userToken == null) {
      throw JobException.warn(JobExceptionType.SECURE_API_UNAUTHORIZED_USER, "为保证账户安全，请重新登录", "User token cannot be found in DB, userToken {}", token);
    }
    if (userToken.getTimeExpired() < Clock.now()) {
      throw JobException.warn(JobExceptionType.SECURE_API_UNAUTHORIZED_USER, "为保证账户安全，请重新登录", "ser login token expired, token {}", token);
    }
  }

  public UserToken getByToken(String token) {
    UserToken userToken = userTokenMapper.findValidByToken(token);
    if (userToken == null) {
      throw JobException.error("can not find record by token {}", token);
    }
    return userToken;
  }

  public void disableToken(String token) {
    UserToken userToken = getByToken(token);
    userToken.setStatus(AvailabilityStatus.DISABLED.code);
    userTokenMapper.updateById(userToken);
  }
}
