package com.buer.job.service.cache;

import com.buer.job.enums.ArticleCntType;
import com.buer.job.enums.JobCacheNameSpace;
import com.buer.job.service.cache.inter.IRedisCacheProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by jiewu on 2021/2/8
 */
@Component
public class ArticleCntLoader extends AbstractRedisCache<Long, ArticleCntType> {

  @Autowired
  public ArticleCntLoader(IRedisCacheProvider cacheProvider) {
    super(cacheProvider, String.valueOf(JobCacheNameSpace.ARTICLE_CNT_KEY.code));
  }

  public void incrCnt(Long articleId, ArticleCntType articleCntType) {
    hincrby(articleId, articleCntType, 1);
  }

  public int getCnt(Long articleId, ArticleCntType articleCntType) {
    Map<Object, Object> valMap = hgetAll(articleId);
    return (Integer) valMap.getOrDefault(articleCntType, 0);
  }
}
