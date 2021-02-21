package com.buer.job.controller.admin;

import com.buer.job.exception.JobException;
import com.buer.job.exception.JobExceptionType;
import com.buer.job.response.Result;
import com.buer.job.service.article.ArticleService;
import com.buer.job.service.author.AuthorService;
import com.buer.job.service.company.CompanyService;
import com.buer.job.service.job.JobService;
import com.buer.job.utils.Clock;
import com.buer.job.utils.ResponseUtil;
import com.buer.job.utils.filestorage.FileType;
import com.buer.job.utils.filestorage.IFileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by jiewu on 2021/2/21
 */
@RestController
// TODO(JIEWU,T0000)之后将这些逻辑移动到admin去
public class InternalController {
  // 访问需要的key
  private static final String KEY = "asdasdasdasdqweqweqweq1123123123mmmmmmnnasdsd";

  @Autowired
  private AuthorService authorService;
  @Autowired
  private ArticleService articleService;
  @Autowired
  private CompanyService companyService;
  @Autowired
  private JobService jobService;
  @Autowired
  private IFileStorage fileStorage;


  @PostMapping("/admin/add/article")
  public Result insert(@RequestBody @Valid AdminArticleRequest request,
                       @RequestPart("titlePage") MultipartFile titlePage) {
    if (!KEY.equals(request.key)) {
      throw JobException.error("key不对应 key is {}", request.key);
    }
    String key;
    try {
      key = fileStorage.uploadFile(titlePage.getBytes(), Clock.now() + request.title, FileType.IMAGE);
    } catch (IOException e) {
      throw JobException.warn(JobExceptionType.COMMON_CUSTOM_MESSAGE, "上传图片error", e);
    }
    articleService.insert(request.authorId, request.title, request.articleType, request.articleSource, request.creationType, request.introduction, request.content, key);
    return ResponseUtil.originOk();
  }

  @PostMapping("/admin/add/author")
  public Result insertAuthor(@RequestBody @Valid AdminAuthorRequest request,
                             @RequestPart("imageHeader") MultipartFile imageHeader) {
    if (!KEY.equals(request.key)) {
      throw JobException.error("key不对应 key is {}", request.key);
    }
    String key;
    try {
      key = fileStorage.uploadFile(imageHeader.getBytes(), Clock.now() + request.name, FileType.IMAGE);
    } catch (IOException e) {
      throw JobException.warn(JobExceptionType.COMMON_CUSTOM_MESSAGE, "上传图片error", e);
    }
    authorService.insert(request.name, key);
    return ResponseUtil.originOk();
  }

  @PostMapping("/admin/add/company")
  public Result insertCompany(@RequestBody @Valid AdminCompanyRequest request,
                              @RequestPart("companyLogo") MultipartFile companyLogo) {
    if (!KEY.equals(request.key)) {
      throw JobException.error("key不对应 key is {}", request.key);
    }
    String key;
    try {
      key = fileStorage.uploadFile(companyLogo.getBytes(), Clock.now() + request.companyName, FileType.IMAGE);
    } catch (IOException e) {
      throw JobException.warn(JobExceptionType.COMMON_CUSTOM_MESSAGE, "上传图片error", e);
    }
    companyService.insert(request.companyName, request.profile, request.address, key);
    return ResponseUtil.originOk();
  }

  @PostMapping("/admin/add/job")
  public Result insertJob(@RequestBody @Valid AdminJobRequest request) {
    if (!KEY.equals(request.key)) {
      throw JobException.error("key不对应 key is {}", request.key);
    }
    jobService.insert(request.companyId, request.workType, request.jobName, request.workCity,
        request.jobType, request.publishType, request.publishTime,
        request.minSalary, request.maxSalary, request.salaryType,
        request.jobIntroduction, request.jobRequirements, request.tags, request.email);
    return ResponseUtil.originOk();
  }
}
