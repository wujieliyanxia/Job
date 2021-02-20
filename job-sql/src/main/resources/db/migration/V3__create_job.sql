CREATE TABLE `company`
(
    `ID`                    bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `NAME`                  varchar(100)    NOT NULL COMMENT '公司名称',
    `LOGO_KEY`              varchar(100)    DEFAULT NULL COMMENT '公司logo',
    `PROFILE`               text            DEFAULT NULL COMMENT '公司简介',
    `ADRESS`                text            DEFAULT NULL COMMENT '公司地址',
    `TIME_CREATED`          bigint(20)      NOT NULL COMMENT '创建时间',
    `TIME_UPDATED`          bigint(20)      NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`),
    KEY `index__name` (`NAME`)
) COMMENT '公司表';

CREATE TABLE `job`
(
    `ID`                             bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `COMPANY_ID`                     bigint(20)      NOT NULL  COMMENT '公司id',
    `WORK_TYPE`                      char(1)         NOT NULL COMMENT '工作类型，主要有秋招和实习',
    `JOB_NAME`                       varchar(100)    NOT NULL COMMENT '岗位名称',
    `WORK_CITY`                      varchar(100)    NOT NULL COMMENT '工作城市',
    `JOB_TYPE`                       varchar(50)     NOT NULL COMMENT '岗位类型',
    `PUBLISH_TYPE`                   char(1)         NOT NULL COMMENT '发布类型',
    `PUBLISH_TIME`                   bigint(20)      NOT NULL COMMENT '发布时间',
    `MIN_SALARY`                     int(10)         DEFAULT NULL COMMENT '最低薪水',
    `MAX_SALARY`                     int(10)         DEFAULT NULL COMMENT '最高薪水',
    `SALARY_TYPE`                    char(1)         NOT NULL COMMENT '薪水周期，面议，天，周，月，年',
    `JOB_INTRODUCTION`               text            NOT NULL COMMENT '岗位简介',
    `JOB_REQUIREMENTS`               text            NOT NULL COMMENT '岗位要求',
    `JOB_REQUIREMENT_TAGS`           varchar(256)    NOT NULL COMMENT '岗位要求的tag，比如三个月、四天以上',
    `CONTACT_EMAIL`                  varchar(100)    NOT NULL COMMENT '联系人邮箱',
    `TIME_CREATED`                   bigint(20)      NOT NULL COMMENT '创建时间',
    `TIME_UPDATED`                   bigint(20)      NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`),
    KEY `index__compay_id` (`COMPANY_ID`),
    KEY `index__name` (`JOB_NAME`),
    KEY `index__publish_time` (`PUBLISH_TIME`)
) COMMENT '岗位表';
