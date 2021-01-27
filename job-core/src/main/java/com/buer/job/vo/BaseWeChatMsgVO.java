package com.buer.job.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by jiewu on 2021/1/26
 */
@Data
public class BaseWeChatMsgVO {
  @JsonProperty("errcode")
  private String errorCode;
  @JsonProperty("errmsg")
  private String errorMsg;

  @JsonIgnore
  public boolean isError() {
    return StringUtils.isNotBlank(this.getErrorCode());
  }
}
