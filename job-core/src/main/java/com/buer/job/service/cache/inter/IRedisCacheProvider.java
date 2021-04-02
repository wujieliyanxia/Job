package com.buer.job.service.cache.inter;

import java.util.Map;
import java.util.Set;

/**
 * Created by jiewu on 2021/1/26
 */
public interface IRedisCacheProvider {
  String get(String key);

  Boolean set(String key, String value, Long expire);

  void hashPut(String key, String hashKey, String value);

  Boolean hashKey(String key, String hashKey);

  Boolean zsetAdd(String key, String value, double score);

  double zsetGet(String key, String subKey);

  void hincrby(String key, Object hashKey, int cnt);

  Map<Object, Object> hgetAll(String key);

  void sadd(String key, String value);

  boolean isMember(String key, String value);

  Set<String> members(String key);
}
