package com.buer.job.request;

import javax.validation.constraints.NotBlank;

/**
 * Created by jiewu on 2021/1/30
 */
public class UploadWeChatMobileInfo {
  // 微信返回的信息，需要加解密
  @NotBlank
  public String encryptedData;
  @NotBlank
  public String iv;
}
