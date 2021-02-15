package com.buer.job.utils.redis;

import com.buer.job.service.cache.inter.IRedisCacheProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Map;
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
  public void hashPut(String key, String hashKey, String value) {
    redisTemplate.opsForHash().put(key, hashKey, value);
  }

  @Override
  public Boolean hashKey(String key, String hashKey) {
    return redisTemplate.opsForHash().hasKey(key, hashKey);
  }

  @Override
  public Boolean zsetAdd(String key, String value, double score) {
    return redisTemplate.opsForZSet().add(key, value, score);
  }

  @Override
  public double zsetGet(String key, String subKey) {
    return redisTemplate.opsForZSet().score(key, subKey);
  }

  @Override
  public void hincrby(String key, Object hashKey, int cnt) {
    redisTemplate.opsForHash().increment(key, hashKey, cnt);
  }

  @Override
  public Map<Object, Object> hgetAll(String key) {
    return redisTemplate.opsForHash().entries(key);
  }

  @Override
  public void sadd(String key, String value) {
    redisTemplate.opsForSet().add(key, value);
  }

  @Override
  public boolean isMember(String key, String value) {
    return redisTemplate.opsForSet().isMember(key, value);
  }

  @Override
  public String get(final String key) {
    ValueOperations<String, String> operations = redisTemplate.opsForValue();
    return operations.get(key);
  }
}
