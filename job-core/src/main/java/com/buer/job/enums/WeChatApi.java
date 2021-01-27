package com.buer.job.enums;

public enum WeChatApi {
  JS_CODE_2_SESSION("https://api.weixin.qq.com/sns/jscode2session", "authorization_code"),
  GET_ACCESS_TOKEN("https://api.weixin.qq.com/cgi-bin/token", "client_credential"),
  ;

  public String url;
  public String grantType;


  WeChatApi(String url, String grantType) {
    this.url = url;
    this.grantType = grantType;
  }
}
