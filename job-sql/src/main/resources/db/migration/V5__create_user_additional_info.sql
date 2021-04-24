CREATE TABLE `user_additional_info`
(
    `ID`                    bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `USER_ID`               bigint(20)      NOT NULL COMMENT '用户id',
    `SCHOOL`                varchar(100)    DEFAULT NULL COMMENT '用户的学校',
    `SEX`                   char(1)         DEFAULT NULL COMMENT '性别',
    `HEADER_PIC`            varchar(100)    DEFAULT NULL COMMENT '用户头像',
    `GRADUATION_TIME`       bigint(20)      DEFAULT NULL COMMENT '毕业时间',
    `TIME_CREATED`          bigint(20)      NOT NULL COMMENT '创建时间',
    `TIME_UPDATED`          bigint(20)      NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `uIndex__user_id` (`USER_ID`),
    KEY `index__time_created` (`TIME_CREATED`)
) COMMENT '用户额外信息表';
