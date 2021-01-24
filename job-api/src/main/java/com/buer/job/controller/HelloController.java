package com.buer.job.controller;

import com.buer.job.model.entity.Test;
import com.buer.job.model.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jiewu on 2021/1/23
 */
@RestController
public class HelloController {
  @Autowired
  private TestMapper testMapper;

  @RequestMapping("/hello/{id}")
  public void test(@PathVariable(name = "id") String id) {
    Test test = new Test();
    test.setName(id);
    testMapper.insert(test);

  }

  @RequestMapping("/hello1/aaa")
  public void test() {

  }

  @RequestMapping("/hello1/bbb")
  public void test1() {

  }
}
