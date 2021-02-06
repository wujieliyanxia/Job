package com.buer.job.model.mapper;

import com.buer.job.model.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buer.job.model.entity.UserToken;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * ??? Mapper 接口
 * </p>
 *
 * @author jiewu
 * @since 2021-02-06
 */
public interface ArticleMapper extends BaseMapper<Article> {
  @Select("select * from article where  time_publish < #{endTime} order by time_publish desc limit #{limit}")
  List<Article> findByTimePublish(@Param("endTime") Long endTime, @Param("limit") Integer limit);

  @Select("select * from article order by time_publish desc limit #{limit}")
  List<Article> find(@Param("limit") Integer limit);
}
