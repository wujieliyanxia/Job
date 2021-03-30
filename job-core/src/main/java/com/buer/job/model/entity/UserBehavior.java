package com.buer.job.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 用户行为表
 * </p>
 *
 * @author jiewu
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserBehavior implements Serializable {

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
     * 用户行为类别,收藏，浏览，报名、或者移除收藏等等
     */
    @TableField("TYPE")
    private String type;

    /**
     * 对应的目标id
     */
    @TableField("TARGET_ID")
    private Long targetId;

    /**
     * 创建时间
     */
    @TableField("TIME_CREATED")
    private Long timeCreated;


}
