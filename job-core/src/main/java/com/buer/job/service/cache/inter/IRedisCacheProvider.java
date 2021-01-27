package com.buer.job.service.cache.inter;

/**
 * Created by jiewu on 2021/1/26
 */
public interface IRedisCacheProvider {
  String get(String key);

  Boolean set(String key, String value, Long expire);
}
