package com.buer.job.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ??token?
 * </p>
 *
 * @author jiewu
 * @since 2021-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserToken implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ??
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * ?????id???????
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * token
     */
    @TableField("TOKEN")
    private String token;

    /**
     * E???D??
     */
    @TableField("STATUS")
    private String status;

    /**
     * ????
     */
    @TableField("TIME_EXPIRED")
    private Long timeExpired;

    /**
     * ????
     */
    @TableField("TIME_CREATED")
    private Long timeCreated;

    /**
     * ????
     */
    @TableField("TIME_UPDATED")
    private Long timeUpdated;


}
