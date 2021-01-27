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
public class WechatInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ??
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * user??id
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * ???UNION_ID???????
     */
    @TableField("UNION_ID")
    private String unionId;

    /**
     * ???openid,???????
     */
    @TableField("OPEN_ID")
    private String openId;

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

    /**
     * ???????????
     */
    @TableField("USER_INFO")
    private String userInfo;


}
