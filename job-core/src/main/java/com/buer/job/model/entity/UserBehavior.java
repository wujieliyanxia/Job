package com.buer.job.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ?????
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
     * ??
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * ??id
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * ??????,?????????????????
     */
    @TableField("TYPE")
    private String type;

    /**
     * ?????id
     */
    @TableField("TARGET_ID")
    private Long targetId;

    /**
     * ????
     */
    @TableField("TIME_CREATED")
    private Long timeCreated;


}
