CREATE TABLE `user`
(
    `ID`            bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键',
    `MOBILE_NUMBER` varchar(50) DEFAULT NULL COMMENT '手机号',
    `PASSWORD`      varchar(70) DEFAULT NULL COMMENT '密码',
    `IS_VERIFIED`   char(1)     NOT NULL DEFAULT 'V' COMMENT '有效用户(V), 无效用户(U)',
    `USER_TYPE`     char(1)     NOT NULL COMMENT '用户类型',
    `TIME_CREATED`  bigint(20)  NOT NULL COMMENT '创建时间',
    `TIME_UPDATED`  bigint(20)  NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `uindex__mobile_number` (`MOBILE_NUMBER`),
    KEY `index__time_created` (`TIME_CREATED`)
) COMMENT '用户表';

CREATE TABLE `wechat_info`
(
    `ID`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `USER_ID`       bigint(20)   NOT NULL COMMENT 'user表的id',
    `UNION_ID`      varchar(100) DEFAULT NULL COMMENT '微信的UNION_ID，全小程序唯一',
    `OPEN_ID`       varchar(70)  DEFAULT NULL COMMENT '微信的openid,单个小程序唯一',
    `TIME_CREATED`  bigint(20)   NOT NULL COMMENT '创建时间',
    `TIME_UPDATED`  bigint(20)   NOT NULL COMMENT '更新时间',
    `USER_INFO`     text         DEFAULT NULL COMMENT '微信返回的用户详细信息',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `uindex__open_id` (`OPEN_ID`),
    UNIQUE KEY `uindex__union_id` (`UNION_ID`),
    KEY `index__time_created` (`TIME_CREATED`)
) COMMENT '用户表';

CREATE TABLE `user_token` (
    `ID`                  bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '主键',
    `USER_ID`             bigint(20)    NOT NULL COMMENT '登录人员的id，在我们这存的',
    `TOKEN`               varchar(50)   NOT NULL COMMENT 'token',
    `STATUS`              char (1)      NOT NULL COMMENT 'E有效，D无效',
    `TIME_EXPIRED`        bigint(20)    NOT NULL COMMENT '过期时间',
    `TIME_CREATED`        bigint(20)    NOT NULL COMMENT '创建时间',
    `TIME_UPDATED`        bigint(20)    NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`),
    KEY `index__user_id` (`USER_ID`),
    UNIQUE KEY `uindex__token` (`TOKEN`),
    KEY `index__time_created` (`TIME_CREATED`)
) comment '用户token表';

