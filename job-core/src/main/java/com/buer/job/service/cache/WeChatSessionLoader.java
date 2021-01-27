package com.buer.job.service.cache;

import com.buer.job.enums.JobCacheNameSpace;
import com.buer.job.service.cache.inter.IRedisCacheProvider;
import com.buer.job.utils.redis.RedisCache;
import com.buer.job.vo.WeChatSessionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiewu on 2021/1/26
 */
@Component
public class WeChatSessionLoader extends AbstractRedisCache<Long, WeChatSessionVO> {
  @Autowired
  public WeChatSessionLoader(IRedisCacheProvider cacheProvider) {
    super(cacheProvider, String.valueOf(JobCacheNameSpace.WE_CHAT_SESSION_KEY.code));
  }

  public WeChatSessionVO getByUserId(Long userId) {
    return get(userId);
  }
}
