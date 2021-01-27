package com.buer.job.model.mapper;

import com.buer.job.model.entity.WechatInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * ??? Mapper 接口
 * </p>
 *
 * @author jiewu
 * @since 2021-01-26
 */
public interface WechatInfoMapper extends BaseMapper<WechatInfo> {
  @Select("select * from wechat_info where open_id = #{openId}")
  WechatInfo findByOpenId(@Param("openId") String openId);
}
