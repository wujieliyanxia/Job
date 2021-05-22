package com.buer.job.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buer.job.model.entity.UserBehavior;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户行为表 Mapper 接口
 * </p>
 *
 * @author jiewu
 * @since 2021-03-30
 */
public interface UserBehaviorMapper extends BaseMapper<UserBehavior> {
  @Select("select target_id from user_behavior where user_id = #{userId} and type = #{type} and source = #{source} limit 100")
  List<Long> fetchByParam(@Param("userId") Long userId, @Param("type") String type, @Param("source") String source);

  @Select("select * from user_behavior where user_id = #{userId} and target_id =  #{targetId} and type = #{type} and source = #{source} limit 100")
  UserBehavior fetchByUniqueKey(@Param("userId") Long userId, @Param("targetId") Long targetId, @Param("type") String type, @Param("source") String source);
}
