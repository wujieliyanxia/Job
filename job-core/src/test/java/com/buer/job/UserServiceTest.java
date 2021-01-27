package com.buer.job;

import com.buer.job.service.user.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jiewu on 2021/1/27
 */
public class UserServiceTest extends BaseTest {
  @Autowired
  private UserService userService;

  @Test
  public void testInit() {
    userService.initUser("1125");
  }

}
