package com.buer.job.utils;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * Created by jiewu on 2021/1/24
 */
public class CodeGenerator {
  /**
   * <p>
   * 读取控制台内容
   * </p>
   */
  public static String scanner(String tip) {
    Scanner scanner = new Scanner(System.in);
    StringBuilder help = new StringBuilder();
    help.append("请输入" + tip + "：");
    System.out.println(help.toString());
    if (scanner.hasNext()) {
      String ipt = scanner.next();
      if (StringUtils.isNotBlank(ipt)) {
        return ipt;
      }
    }
    throw new MybatisPlusException("请输入正确的" + tip + "！");
  }

  public static void main(String[] args) {
    // 代码生成器
    AutoGenerator mpg = new AutoGenerator();

    // 全局配置
    GlobalConfig gc = new GlobalConfig();
    String projectPath = System.getProperty("user.dir");
    gc.setOutputDir(projectPath + "/job-core/src/main/java");
    gc.setAuthor("jiewu");
    gc.setOpen(false);
    // gc.setSwagger2(true); 实体属性 Swagger2 注解
    mpg.setGlobalConfig(gc);

    // 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setUrl("jdbc:mysql://localhost:3306/job_mock?characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8");
    dsc.setDriverName("com.mysql.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("");
    mpg.setDataSource(dsc);

    // 包配置
    PackageConfig pc = new PackageConfig();
    pc.setModuleName("model");
    pc.setParent("com.buer.job");
    mpg.setPackageInfo(pc);

    // 自定义配置
    InjectionConfig cfg = new InjectionConfig() {
      @Override
      public void initMap() {
        // to do nothing
      }
    };

    // 如果模板引擎是 freemarker
//    String templatePath = "/templates/mapper.xml.ftl";

    mpg.setCfg(cfg);

    // 配置模板
    TemplateConfig templateConfig = new TemplateConfig().disable(TemplateType.SERVICE,TemplateType.CONTROLLER);

    templateConfig.setXml(null);
    mpg.setTemplate(templateConfig);

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    strategy.setEntityLombokModel(true);
    strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
    strategy.setTablePrefix(pc.getModuleName() + "_");
    mpg.setStrategy(strategy);
    mpg.setTemplateEngine(new VelocityTemplateEngine());
    mpg.execute();
  }
}
