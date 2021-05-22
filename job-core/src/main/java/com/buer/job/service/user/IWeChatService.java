package com.buer.job.service.user;

import com.buer.job.vo.WeChatAccessTokenVO;
import com.buer.job.vo.WeChatSessionVO;

import java.util.concurrent.ExecutionException;

public interface IWeChatService {
  WeChatSessionVO login(String code);

  WeChatAccessTokenVO getAccessToken() throws ExecutionException;
}
