package com.buer.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by jiewu on 2021/1/23
 */
@MapperScan("com.buer.job.mapper")
@SpringBootApplication
public class JobApiApplication {
  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(JobApiApplication.class);
    app.run(args);
  }
}
