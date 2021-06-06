package com.buer.job.service.article;

import com.buer.job.enums.ArticleType;
import com.buer.job.enums.BehaviorSource;
import com.buer.job.enums.BehaviorType;
import com.buer.job.exception.JobException;
import com.buer.job.model.entity.Article;
import com.buer.job.model.mapper.ArticleMapper;
import com.buer.job.service.behavior.UserBehaviorService;
import com.buer.job.service.cache.ArticleCntLoader;
import com.buer.job.service.mongo.ArticleMongoModel;
import com.buer.job.utils.Clock;
import com.buer.job.vo.ArticleDetailVO;
import com.buer.job.vo.ArticleSimpleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by jiewu on 2021/2/6
 */
@Service
public class ArticleService {
  @Autowired
  private ArticleMapper articleMapper;
  @Autowired
  private ArticleMongoModel articleMongoModel;
  @Autowired
  private ArticleCntLoader articleCntLoader;
  @Autowired
  private UserBehaviorService userBehaviorService;


  public Article insert(Long authorId, String title, ArticleType articleType,
                        String articleSource, String creationType,
                        String introduction, String content, String articleImageKey) {
    String objectId = articleMongoModel.insert(content);
    Article article = new Article();
    article.setAuthorId(authorId);
    article.setTitle(title);
    article.setArticleType(articleType.name());
    article.setArticleSource(articleSource);
    article.setCreationType(creationType);
    article.setIntroduction(introduction);
    article.setContentKey(objectId);
    article.setArticleImageKey(articleImageKey);
    Long now = Clock.now();
    // 默认创建时间就是文章的发布时间
    article.setTimePublish(now);
    article.setTimeCreated(now);
    article.setTimeUpdated(now);
    articleMapper.insert(article);
    return article;
  }

  public List<ArticleSimpleVO> getListVO(ArticleType articleType, Long endTime, Integer limit) {
    List<Article> articles = endTime == null ? articleMapper.find(articleType.name(), limit) : articleMapper.findByTimePublishAndArticleType(articleType.name(), endTime, limit);
    if (CollectionUtils.isEmpty(articles)) {
      return new ArrayList<>();
    }
    return articles.stream().map(ArticleSimpleVO::from).collect(Collectors.toList());
  }

  public List<ArticleSimpleVO> getListVO(Collection<Long> articleIds) {
    if (CollectionUtils.isEmpty(articleIds)) {
      return new ArrayList<>();
    }
    List<Article> articles = articleMapper.selectBatchIds(articleIds);
    if (CollectionUtils.isEmpty(articles)) {
      return new ArrayList<>();
    }
    return articles.stream().map(ArticleSimpleVO::from).collect(Collectors.toList());
  }

  public ArticleDetailVO getArticleDetail(Long id) {
    Article article = findByIdOrThrow(id);
    return ArticleDetailVO.from(article, articleMongoModel.findByObjectID(article.getContentKey()));
  }

  public Article findByIdOrThrow(Long id) {
    Article article = articleMapper.selectById(id);
    return Optional.ofNullable(article).orElseThrow(() -> JobException.error("can not find article by id,id is {}", id)
    );
  }

  public int getArticleCnt(Long articleId, BehaviorType behaviorType) {
    return userBehaviorService.getCnt(articleId, behaviorType, BehaviorSource.ARTICLE);
  }

  public boolean userViewedArticle(Long articleId, Long userId) {
    return userBehaviorService.viewed(userId, articleId, BehaviorSource.ARTICLE);
  }
}
