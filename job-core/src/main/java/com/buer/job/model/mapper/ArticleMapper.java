package com.buer.job.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buer.job.model.entity.Article;
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
  @Select("select * from article where  time_publish < #{endTime} and article_type = #{articleType} order by time_publish desc limit #{limit}")
  List<Article> findByTimePublishAndArticleType(@Param("articleType") String articleType, @Param("endTime") Long endTime, @Param("limit") Integer limit);

  @Select("select * from article where article_type = #{articleType} order by time_publish desc limit #{limit}")
  List<Article> find(@Param("articleType") String articleType, @Param("limit") Integer limit);
}
