CREATE TABLE `author`
(
    `ID`                    bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `NAME`                  varchar(100)    NOT NULL COMMENT '作者姓名',
    `HEADER_IMAGE_KEY`      varchar(100)    DEFAULT NULL COMMENT '作者头像',
    `TIME_CREATED`          bigint(20)      NOT NULL COMMENT '创建时间',
    `TIME_UPDATED`          bigint(20)      NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`),
    KEY `index__name` (`NAME`)
) COMMENT '作者表';

CREATE TABLE `article`
(
    `ID`                 bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `AUTHOR_ID`          bigint(20)      NOT NULL COMMENT '作者id',
    `TITLE`              text            NOT NULL COMMENT '文章题目',
    `ARTICLE_IMAGE_KEY`  varchar(50)     NOT NULL COMMENT '文章封面',
    `ARTICLE_TYPE`       varchar(50)     NOT NULL COMMENT '文章类型',
    `ARTICLE_SOURCE`     varchar(100)    DEFAULT NULL COMMENT '文章来源',
    `CREATION_TYPE`      varchar(100)    DEFAULT NULL COMMENT '创作类型',
    `CONTENT_KEY`        varchar(50)     NOT NULL NULL COMMENT '文章内容，对应的是mongoKey',
    `INTRODUCTION`       text            DEFAULT NULL COMMENT '简介',
    `TIME_PUBLISH`       bigint(20)      NOT NULL COMMENT '文章发布时间',
    `TIME_CREATED`       bigint(20)      NOT NULL COMMENT '创建时间',
    `TIME_UPDATED`       bigint(20)      NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`),
    KEY `index__author_id` (`AUTHOR_ID`),
    KEY `index__article_type` (`ARTICLE_TYPE`),
    KEY `index__article_source` (`ARTICLE_SOURCE`),
    KEY `index__creation_type` (`CREATION_TYPE`)
) COMMENT '文章表';

