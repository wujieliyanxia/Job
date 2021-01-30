package com.buer.job.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by jiewu on 2021/1/30
 */
@Data
public class WeChatWaterMaker {
  @JsonProperty("appid")
  private String appId;
  private String timestamp;
}
