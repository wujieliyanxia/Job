package com.buer.job.utils.redis;

import com.buer.job.utils.Clock;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.Callable;

@Component
@Slf4j
public class RedisLocker<V> {
  @Autowired
  private StringRedisTemplate redisTemplate;

  /**
   * redis 锁过期时间
   */
  private static final long EXPIRE_TIME = 5 * 60;
  private static final long NO_TIME_OUT_FLAG = -1L;
  private static final String SET_NX_WITH_EXPIRE = "if (redis.call('setnx', KEYS[1], ARGV[1]) < 1)" +
          "then return 0;" +
          "end;" +
          "redis.call('expire', KEYS[1], tonumber(ARGV[2]));" +
          "return 1;";

  private static final String DEL = "if (redis.call('get', KEYS[1]) == ARGV[1])" +
          "then redis.call('del', KEYS[1]);" +
          "return 1;" +
          "end;" +
          "return 0;";
  private ThreadLocal<String> threadLocal = new ThreadLocal();

  public boolean tryLockWithTimeout(String key, String requestId, long timeout) {
    long startTime = Clock.now();
    while (!tryLock(key, requestId)) {
      if (timeout == NO_TIME_OUT_FLAG) {
        return false;
      }
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (Clock.now() - startTime > timeout) {
        return false;
      }
    }
    return true;
  }

  private boolean tryLock(String key, String requestId) {
    DefaultRedisScript<Long> script = new DefaultRedisScript(SET_NX_WITH_EXPIRE, Long.class);
    threadLocal.set(requestId);
    Long res = redisTemplate.execute(script, Lists.newArrayList(key), requestId, String.valueOf(EXPIRE_TIME));
    return res.equals(1L);
  }

  private Long release(String key, String requestId) {
    DefaultRedisScript<Long> script = new DefaultRedisScript(DEL,Long.class);
    return redisTemplate.execute(script, Lists.newArrayList(key), requestId);
  }

  /**
   * 当获取不到锁的时候，直接退出
   *
   * @param key
   */
  public void lock(String key, Runnable runnable) {
    lockWithTimeout(key, runnable, NO_TIME_OUT_FLAG);
  }

  public V lockWithResult(String key, Callable<V> callable) throws Exception {
    return lockWithResultAndTimeout(key, callable, NO_TIME_OUT_FLAG);
  }

  public void lockWithTimeout(String key, Runnable runnable, long timeout) {
    String requestId = UUID.randomUUID().toString();
    if (!tryLockWithTimeout(key, requestId, timeout)) {
      return;
    }
    try {
      runnable.run();
    } finally {
      release(key, threadLocal.get());
    }
  }


  public V lockWithResultAndTimeout(String key, Callable<V> callable, long timeout) throws Exception {
    String requestId = UUID.randomUUID().toString();
    if (!tryLockWithTimeout(key, requestId, timeout)) {
      return null;
    }
    try {
      return callable.call();
    } finally {
      release(key, threadLocal.get());
    }
  }
}
