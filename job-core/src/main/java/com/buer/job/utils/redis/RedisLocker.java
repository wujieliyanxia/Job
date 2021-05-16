package com.buer.job.utils.redis;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RedisLocker {
  @Autowired
  private StringRedisTemplate redisTemplate;

  private static final long EXPIRE_TIME = 5 * 60;
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

  private boolean tryLock(String key, String requestId) {
    DefaultRedisScript<Integer> script = new DefaultRedisScript();
    script.setResultType(Integer.class);
    script.setScriptText(SET_NX_WITH_EXPIRE);
    threadLocal.set(requestId);
    Integer res = redisTemplate.execute(script, Lists.newArrayList(key), EXPIRE_TIME, requestId);
    return res == 1;
  }

  private int release(String key, String requestId) {
    DefaultRedisScript<Integer> script = new DefaultRedisScript();
    script.setResultType(Integer.class);
    script.setScriptText(DEL);
    return redisTemplate.execute(script, Lists.newArrayList(key), requestId);
  }

  public void lockWithoutTimeout(String key) {
    try {
      String requestId = UUID.randomUUID().toString();
      while (!tryLock(key, requestId)) {
        // do nothing
      }
    } finally {
      release(key, threadLocal.get());
    }
  }
}
