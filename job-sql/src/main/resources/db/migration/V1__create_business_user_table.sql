CREATE TABLE `business_user`
(
    `ID`            bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `BUSINESS_UUID` varchar(100) NOT NULL COMMENT '业务用户唯一id',
    `BUSINESS_TYPE` varchar(20)  NOT NULL COMMENT '业务类型',
    `IS_VERIFIED`   char(1)      NOT NULL DEFAULT 'V' COMMENT '有效用户(V), 无效用户(U)',
    `TIME_CREATED`  bigint(20)   NOT NULL COMMENT '创建时间',
    `TIME_UPDATED`  bigint(20)   NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`ID`),
    UNIQUE KEY `uindex__business_uuid_type` (`BUSINESS_UUID`, `BUSINESS_TYPE`)
)COMMENT '业务用户';