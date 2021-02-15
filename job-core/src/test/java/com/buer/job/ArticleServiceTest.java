package com.buer.job;

import com.buer.job.enums.ArticleType;
import com.buer.job.model.entity.Article;
import com.buer.job.service.article.ArticleService;

import com.buer.job.utils.Clock;
import com.buer.job.vo.ArticleDetailVO;
import com.buer.job.vo.ArticleSimpleVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by jiewu on 2021/2/6
 */
public class ArticleServiceTest extends BaseTest {
  @Autowired
  private ArticleService articleService;

  @Test
  public void testInsert() {
    Article article = articleService.insert(1L, "title1", ArticleType.GOOD_ARTICLE, "buer", "原创", "this is introduction", "this is content html", "mock imageKey");
    ArticleDetailVO detailVO = articleService.getArticleDetail(article.getId());
    Assert.assertNotNull(detailVO);
    Assert.assertEquals("this is content html", detailVO.getHtmlContent());
    List<ArticleSimpleVO> listVO = articleService.getListVO(ArticleType.GOOD_ARTICLE, Clock.now(), 10);
    Assert.assertTrue(listVO.size() >= 1);
  }
}
