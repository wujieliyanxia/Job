package com.buer.job.service.user;

import com.buer.job.conditional.DevConditional;
import com.buer.job.vo.WeChatAccessTokenVO;
import com.buer.job.vo.WeChatSessionVO;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Conditional(value = {DevConditional.class})
public class MockWeChatService implements IWeChatService{
  @Override
  public WeChatSessionVO login(String code) {
    WeChatSessionVO weChatSessionVO = new WeChatSessionVO();
    weChatSessionVO.setOpenId("wujie11111");
    weChatSessionVO.setSessionKey("wujieSessiom");
    return weChatSessionVO;
  }

  @Override
  public WeChatAccessTokenVO getAccessToken() throws ExecutionException {
    return null;
  }
}
