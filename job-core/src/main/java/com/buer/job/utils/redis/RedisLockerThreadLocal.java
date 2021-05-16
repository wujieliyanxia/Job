package com.buer.job.utils.redis;

import java.util.UUID;

public class RedisLockerThreadLocal extends ThreadLocal<String> {
  public String initialValue() {
    return UUID.randomUUID().toString();
  }
}
