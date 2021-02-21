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
 * @since 2021-02-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Job implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ??
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * ??id
     */
    @TableField("COMPANY_ID")
    private Long companyId;

    /**
     * ?????????????
     */
    @TableField("WORK_TYPE")
    private String workType;

    /**
     * ????
     */
    @TableField("JOB_NAME")
    private String jobName;

    /**
     * ????
     */
    @TableField("WORK_CITY")
    private String workCity;

    /**
     * ????
     */
    @TableField("JOB_TYPE")
    private String jobType;

    /**
     * ????
     */
    @TableField("PUBLISH_TYPE")
    private String publishType;

    /**
     * ????
     */
    @TableField("PUBLISH_TIME")
    private Long publishTime;

    /**
     * ????
     */
    @TableField("MIN_SALARY")
    private Integer minSalary;

    /**
     * ????
     */
    @TableField("MAX_SALARY")
    private Integer maxSalary;

    /**
     * ???????????????
     */
    @TableField("SALARY_TYPE")
    private String salaryType;

    /**
     * ????
     */
    @TableField("JOB_INTRODUCTION")
    private String jobIntroduction;

    /**
     * ????
     */
    @TableField("JOB_REQUIREMENTS")
    private String jobRequirements;

    /**
     * ?????tag???????????
     */
    @TableField("JOB_REQUIREMENT_TAGS")
    private String jobRequirementTags;

    /**
     * ?????
     */
    @TableField("CONTACT_EMAIL")
    private String contactEmail;

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
