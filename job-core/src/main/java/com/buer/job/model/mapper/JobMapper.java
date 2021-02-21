package com.buer.job.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buer.job.model.entity.Job;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * ??? Mapper 接口
 * </p>
 *
 * @author jiewu
 * @since 2021-02-20
 */
public interface JobMapper extends BaseMapper<Job> {
  @Select("select * from job where  publish_time < #{endTime} and work_type = #{workType} order by publish_time desc limit #{limit}")
  List<Job> findByTimePublishAndWorkType(@Param("workType") String workType, @Param("endTime") Long endTime, @Param("limit") Integer limit);

  @Select("select * from job where work_type = #{workType} order by publish_time desc limit #{limit}")
  List<Job> find(@Param("workType") String workType, @Param("limit") Integer limit);
}
