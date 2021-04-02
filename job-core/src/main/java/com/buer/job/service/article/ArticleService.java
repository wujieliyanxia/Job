package com.buer.job.service.article;

import com.buer.job.enums.ArticleCntType;
import com.buer.job.enums.ArticleType;
import com.buer.job.exception.JobException;
import com.buer.job.model.entity.Article;
import com.buer.job.model.mapper.ArticleMapper;
import com.buer.job.service.cache.ArticleCntLoader;
import com.buer.job.service.cache.UserForwardLoader;
import com.buer.job.service.cache.UserLikeLoader;
import com.buer.job.service.cache.UserViewLoader;
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
  private UserViewLoader userViewLoader;
  @Autowired
  private UserLikeLoader userLikeLoader;
  @Autowired
  private UserForwardLoader userForwardLoader;

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

  public List<ArticleSimpleVO> getListVOWithUserBehavior(Long userId, ArticleCntType articleCntType) {
    Collection<Long> ids;
    switch (articleCntType) {
      case VIEW_CNT:
        ids = userViewLoader.members(userId);
        break;
      case LIKE_CNT:
        ids = userLikeLoader.members(userId);
        break;
      case FORWARD_CNT:
        ids = userForwardLoader.members(userId);
        break;
      default:
        throw JobException.error("unsupported articleCntType {},userId is {}", articleCntType, userId);
    }
    return getListVO(ids);
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

  public void updateCnt(Long articleId, Long userId, ArticleCntType articleCntType) {
    articleCntLoader.incrCnt(articleId, articleCntType);
    switch (articleCntType) {
      case LIKE_CNT:
        userLikeLoader.sadd(userId, articleId);
        break;
      case VIEW_CNT:
        userViewLoader.sadd(userId, articleId);
        break;
      case FORWARD_CNT:
        userForwardLoader.sadd(userId, articleId);
        break;
      default:
        throw JobException.error("unsupported articleCntType {}", articleCntType);
    }
  }

  public int getArticleCnt(Long articleId, ArticleCntType articleCntType) {
    return articleCntLoader.getCnt(articleId, articleCntType);
  }

  public boolean userViewedArticle(Long articleId, Long userId) {
    return userViewLoader.isMember(userId, articleId);
  }
}
