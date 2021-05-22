package com.buer.job.service.author;

import com.buer.job.exception.JobException;
import com.buer.job.model.entity.Author;
import com.buer.job.model.mapper.AuthorMapper;
import com.buer.job.utils.Clock;
import com.buer.job.vo.AuthorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by jiewu on 2021/2/6
 */
@Service
public class AuthorService {
  @Autowired
  private AuthorMapper authorMapper;

  public Author insert(String name, String headImageKey) {
    Author author = new Author();
    author.setName(name);
    author.setHeaderImageKey(headImageKey);
    Long now = Clock.now();
    author.setTimeCreated(now);
    author.setTimeUpdated(now);
    authorMapper.insert(author);
    return author;
  }

  public AuthorVO findByAuthorIdOrThrow(Long id) {
    Author author = authorMapper.selectById(id);
    return AuthorVO.from(Optional.ofNullable(author)
            .orElseThrow(() -> JobException.error("can not find author by id {}", id)));
  }
}
