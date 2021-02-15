package com.buer.job.service.cache;

import com.buer.job.enums.JobCacheNameSpace;
import com.buer.job.service.cache.inter.IRedisCacheProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jiewu on 2021/2/8
 */
@Component
public class UserViewLoader extends AbstractRedisCache<Long, Long> {
  @Autowired
  public UserViewLoader(IRedisCacheProvider cacheProvider) {
    super(cacheProvider, String.valueOf(JobCacheNameSpace.ARTICLE_USER_VIEW_KEY.code));
  }
}
