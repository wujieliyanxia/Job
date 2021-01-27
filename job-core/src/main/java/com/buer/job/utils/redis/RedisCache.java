package com.buer.job.utils.redis;

import com.buer.job.service.cache.inter.IRedisCacheProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiewu on 2021/1/26
 */
@Component
@Slf4j
public class RedisCache implements IRedisCacheProvider {
  @Autowired
  private StringRedisTemplate redisTemplate;

  @Override
  public Boolean set(final String key, String value, Long expire) {
    ValueOperations operations = redisTemplate.opsForValue();
    operations.set(key, value);
    return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
  }

  @Override
  public String get(final String key) {
    ValueOperations<String, String> operations = redisTemplate.opsForValue();
    return operations.get(key);
  }
}
