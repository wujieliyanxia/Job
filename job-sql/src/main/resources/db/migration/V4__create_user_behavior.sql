CREATE TABLE `user_behavior`
(
    `ID`                    bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `USER_ID`               bigint(20)      NOT NULL COMMENT '用户id',
    `TYPE`                  char(1)         NOT NULL COMMENT '用户行为类别,收藏，浏览，报名、或者移除收藏等等',
    `SOURCE`                char(1)         NOT NULL COMMENT '岗位|文章',
    `TARGET_ID`             bigint(20)      NOT NULL COMMENT '对应的目标id',
    `TIME_CREATED`          bigint(20)      NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`ID`),
    KEY `index__user_source_type` (`USER_ID`,`SOURCE`,`TYPE`),
    KEY `index__target_id` (`TARGET_ID`),
    KEY `index__time_created` (`TIME_CREATED`)
) COMMENT '用户行为表';
