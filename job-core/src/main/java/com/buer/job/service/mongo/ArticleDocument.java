package com.buer.job.service.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by jiewu on 2021/2/6
 */
@Document(collection = "job_article")
@Data
public class ArticleDocument {
  @Id
  private String id;

  private String contentHtml;

}
