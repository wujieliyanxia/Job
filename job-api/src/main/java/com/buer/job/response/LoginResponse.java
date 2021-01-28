package com.buer.job.response;

import lombok.Data;

/**
 * Created by jiewu on 2021/1/28
 */
@Data
public class LoginResponse {
  private String token;

  public static LoginResponse from(String token) {
    LoginResponse response = new LoginResponse();
    response.setToken(token);
    return response;
  }
}
