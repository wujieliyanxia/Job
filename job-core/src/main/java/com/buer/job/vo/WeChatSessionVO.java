package com.buer.job.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by jiewu on 2021/1/26
 */
@Data
public class WeChatSessionVO extends BaseWeChatMsgVO{
  @JsonProperty("openid")
  private String openId;
  @JsonProperty("session_key")
  private String sessionKey;
}
