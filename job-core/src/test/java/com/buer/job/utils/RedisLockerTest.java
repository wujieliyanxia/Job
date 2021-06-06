package com.buer.job.utils;

import com.buer.job.BaseTest;
import com.buer.job.utils.redis.RedisLocker;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisLockerTest extends BaseTest {
  @Autowired
  private RedisLocker redisLocker;

  private int cnt = 0;

  @Test
  public void testLocker() throws InterruptedException {
    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        redisLocker.lockWithTimeout("WUJIE", () -> {
          cnt++;
        }, 50000L);
      }

    });
    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        redisLocker.lockWithTimeout("WUJIE", () -> {
          cnt++;
        }, 50000L);
      }
    });
    thread1.start();
    thread2.start();
    thread1.join();
    thread2.join();
    Assert.assertEquals(Integer.valueOf(cnt), Integer.valueOf(200));
  }
}
