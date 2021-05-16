package com.buer.job.utils;

import com.buer.job.BaseTest;
import com.buer.job.utils.FileUtil;
import com.buer.job.utils.filestorage.FileType;
import com.buer.job.utils.filestorage.IFileStorage;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * Created by jiewu on 2021/2/7
 */
public class FileUtilsServiceTest extends BaseTest {
  @Autowired
  private IFileStorage fileStorage;

  @Test
  @Ignore
  public void testQiniu() throws IOException {
    String login = fileStorage.uploadFile(FileUtil.toByteArray(new File("C:\\Users\\test\\Desktop\\image.png")), "login", FileType.IMAGE);
    System.out.println(login);
  }
}
