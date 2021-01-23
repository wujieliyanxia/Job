package com.buer.flyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.buer.flyway"})
@EnableDiscoveryClient(autoRegister = false)
public class FlywayApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(FlywayApplication.class);
    app.run(args);
  }
}
