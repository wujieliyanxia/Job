package com.buer.job.model.mapper;

import com.buer.job.model.entity.UserToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buer.job.model.entity.WechatInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * ??token? Mapper 接口
 * </p>
 *
 * @author jiewu
 * @since 2021-01-26
 */
public interface UserTokenMapper extends BaseMapper<UserToken> {
  @Select("select * from user_token where token = #{token} and status = 'e' order by id desc limit 1")
  UserToken findValidByToken(@Param("token") String token);
}
