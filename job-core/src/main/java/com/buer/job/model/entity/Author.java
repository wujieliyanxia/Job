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
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Author implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ??
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * ????
     */
    @TableField("NAME")
    private String name;

    /**
     * ????
     */
    @TableField("HEADER_IMAGE_KEY")
    private String headerImageKey;

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
