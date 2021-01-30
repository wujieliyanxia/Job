package com.buer.job.vo;

import lombok.Data;

/**
 * Created by jiewu on 2021/1/30
 */
@Data
public class WeChatMobileNumberVO {
  private String phoneNumber;
  private String purePhoneNumber;
  private String countryCode;
  private WeChatWaterMaker watermark;
}
