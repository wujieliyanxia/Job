package com.buer.job.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ???
 * </p>
 *
 * @author jiewu
 * @since 2021-01-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ??
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * ???
     */
    @TableField("MOBILE_NUMBER")
    private String mobileNumber;

    /**
     * ??
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * ????(V), ????(U)
     */
    @TableField("IS_VERIFIED")
    private String isVerified;

    /**
     * ????
     */
    @TableField("USER_TYPE")
    private String userType;

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
