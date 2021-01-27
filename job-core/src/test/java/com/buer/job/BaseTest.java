package com.buer.job;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@SpringBootTest(
    classes = {Application.class},
    properties = {"spring.cloud.config.uri=http://localhost:8888", "spring.cloud.config.name=job"})
@ActiveProfiles("dev")
@EnableConfigurationProperties
@MapperScan("com.buer.job.model.mapper")
public class BaseTest {
  @Before
  public void clean() {
    log.info("ut start");
  }

  @After
  public void tearDown() {
    log.info("ut end");
  }

}
