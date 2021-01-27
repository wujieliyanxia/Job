package com.buer.job.service.user;

import com.buer.job.enums.UserType;
import com.buer.job.exception.JobException;
import com.buer.job.model.entity.User;
import com.buer.job.model.entity.WechatInfo;
import com.buer.job.model.mapper.UserMapper;
import com.buer.job.model.mapper.WechatInfoMapper;
import com.buer.job.service.cache.WeChatSessionLoader;
import com.buer.job.utils.Clock;
import com.buer.job.vo.WeChatSessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Created by jiewu on 2021/1/26
 */
@Service
public class UserService {
  @Autowired
  private WeChatService weChatService;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private WechatInfoMapper wechatInfoMapper;
  @Autowired
  private UserTokenService userTokenService;
  @Autowired
  private WeChatSessionLoader weChatSessionLoader;
  private static long TOKEN_TIMEOUT = 2 * Clock.SECOND_PER_DAY;
  private static long WE_CHAT_SESSION_TIMEOUT = 2 * Clock.SECOND_PER_DAY;

  public String loginWithWeChat(String code) {
    WeChatSessionVO sessionVO = weChatService.login(code);
    WechatInfo wechatInfo = wechatInfoMapper.findByOpenId(sessionVO.getOpenId());
    User user;

    if (wechatInfo == null) {
      // 这里未来可能会加其他逻辑
      user = initUser(sessionVO.getOpenId());
    } else {
      user = selectByIdOrThrow(wechatInfo.getUserId());
    }

    String token = generateUserToken();
    userTokenService.insertAndInvalidateOldToken(user.getId(), token, getTimeOut());

    weChatSessionLoader.set(user.getId(), sessionVO, WE_CHAT_SESSION_TIMEOUT);
    return token;
  }

  public User selectByIdOrThrow(Long userId) {
    User user = userMapper.selectById(userId);
    if (user == null) {
      throw JobException.error("can not find user by id {}", userId);
    }
    return user;
  }

  public static String generateUserToken() {
    return UUID.randomUUID().toString();
  }

  private Long getTimeOut() {
    return Clock.now() + TOKEN_TIMEOUT;
  }

  @Transactional
  public User initUser(String openId) {
    User user = new User();
    Long now = Clock.now();
    user.setTimeCreated(now);
    user.setTimeUpdated(now);
    user.setUserType(UserType.WE_CHAT.code);
    userMapper.insert(user);

    WechatInfo wechatInfo = new WechatInfo();
    wechatInfo.setOpenId(openId);
    wechatInfo.setUserId(user.getId());
    wechatInfo.setTimeCreated(now);
    wechatInfo.setTimeUpdated(now);
    wechatInfoMapper.insert(wechatInfo);
    return user;
  }
}
