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
public class Article implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ??
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * ??id
     */
    @TableField("AUTHOR_ID")
    private Long authorId;

    /**
     * ????
     */
    @TableField("TITLE")
    private String title;

    /**
     * ????
     */
    @TableField("ARTICLE_TYPE")
    private String articleType;

    /**
     * ????
     */
    @TableField("ARTICLE_SOURCE")
    private String articleSource;

    /**
     * ????
     */
    @TableField("CREATION_TYPE")
    private String creationType;

    /**
     * ?????????mongoKey
     */
    @TableField("CONTENT_KEY")
    private String contentKey;

    /**
     * ??
     */
    @TableField("INTRODUCTION")
    private String introduction;

    /**
     * ??????
     */
    @TableField("TIME_PUBLISH")
    private Long timePublish;

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
