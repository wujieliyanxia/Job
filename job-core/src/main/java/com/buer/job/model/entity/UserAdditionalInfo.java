package com.buer.job.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户额外信息表
 * </p>
 *
 * @author jiewu
 * @since 2021-04-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserAdditionalInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * 用户的学校
     */
    @TableField("SCHOOL")
    private String school;

    /**
     * 性别
     */
    @TableField("SEX")
    private String sex;

    /**
     * 用户头像
     */
    @TableField("HEADER_PIC")
    private String headerPic;

    /**
     * 毕业时间
     */
    @TableField("GRADUATION_TIME")
    private Long graduationTime;

    /**
     * 创建时间
     */
    @TableField("TIME_CREATED")
    private Long timeCreated;

    /**
     * 更新时间
     */
    @TableField("TIME_UPDATED")
    private Long timeUpdated;


}
