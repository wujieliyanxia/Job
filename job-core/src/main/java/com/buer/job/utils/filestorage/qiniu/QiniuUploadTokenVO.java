package com.buer.job.utils.filestorage.qiniu;

/**
 * Created by mavs on 2017/8/22.
 */
public class QiniuUploadTokenVO {
  public String uploadToken;
  public String url;
  public String backUrl;

  public static QiniuUploadTokenVO from(String uploadToken, String url, String backUrl) {
    QiniuUploadTokenVO vo = new QiniuUploadTokenVO();
    vo.uploadToken = uploadToken;
    vo.url = url;
    vo.backUrl = backUrl;
    return vo;
  }
}
