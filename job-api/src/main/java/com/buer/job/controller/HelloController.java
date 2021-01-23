package com.buer.job.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Created by jiewu on 2021/1/23
 */
@RestController
public class HelloController {

  @RequestMapping("/hello/{id}")
  public void test(@PathVariable(name = "id") String id) {
    System.out.println(id);
  }

  @RequestMapping("/hello1/aaa")
  public void test() {

  }

  @RequestMapping("/hello1/bbb")
  public void test1() {

  }
}
