package com.buer.job.model.mapper;

import com.buer.job.model.entity.UserAdditionalInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buer.job.model.entity.UserToken;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户额外信息表 Mapper 接口
 * </p>
 *
 * @author jiewu
 * @since 2021-04-24
 */
public interface UserAdditionalInfoMapper extends BaseMapper<UserAdditionalInfo> {
  @Select("select * from user_additional_info where user_id = #{userId}")
  UserAdditionalInfo findByUserId(@Param("userId") Long userId);
}
