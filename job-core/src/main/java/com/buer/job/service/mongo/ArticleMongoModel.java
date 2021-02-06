package com.buer.job.service.mongo;

import com.buer.job.exception.JobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by jiewu on 2021/2/6
 */
@Repository
public class ArticleMongoModel {
  @Autowired
  private MongoTemplate mongoTemplate;

  public String insert(String htmlContent) {
    ArticleDocument articleDocument = new ArticleDocument();
    articleDocument.setContentHtml(htmlContent);
    return mongoTemplate.insert(articleDocument).getId();
  }

  public String findByObjectID(String objectId) {
    ArticleDocument articleDocument = mongoTemplate.findById(objectId, ArticleDocument.class);
    return Optional.ofNullable(articleDocument)
        .orElseThrow(() -> JobException.error("can not find ArticleDocument by id {}", objectId))
        .getContentHtml();
  }
}
