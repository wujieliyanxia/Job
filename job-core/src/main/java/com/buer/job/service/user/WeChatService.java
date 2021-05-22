package com.buer.job.service.user;

import com.buer.job.conditional.TestOrProdConditional;
import com.buer.job.enums.WeChatApi;
import com.buer.job.exception.JobException;
import com.buer.job.utils.JsonUtils;
import com.buer.job.utils.http.HttpClient;
import com.buer.job.utils.http.ResponseInfo;
import com.buer.job.vo.BaseWeChatMsgVO;
import com.buer.job.vo.WeChatAccessTokenVO;
import com.buer.job.vo.WeChatSessionVO;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiewu on 2021/1/26
 */
@Service
@Conditional(value = {TestOrProdConditional.class})
public class WeChatService implements IWeChatService{
  @Value("${weChat.appId}")
  private String appId;
  @Value("${weChat.secret}")
  private String secret;

  private static final int ACCESS_TOKEN_EXPIRE_SECONDS = 7200;

  private LoadingCache<String, WeChatAccessTokenVO> cache = CacheBuilder
          .newBuilder()
          .maximumSize(10L)
          .expireAfterWrite(ACCESS_TOKEN_EXPIRE_SECONDS, TimeUnit.SECONDS)
          .build(new CacheLoader<String, WeChatAccessTokenVO>() {
            @Override
            public WeChatAccessTokenVO load(String str) {
              return obtainAccessToken();
            }
          });

  @Override
  public WeChatSessionVO login(String code) {
    HttpUrl url = HttpUrl.parse(WeChatApi.JS_CODE_2_SESSION.url).newBuilder()
            .addQueryParameter("appid", appId)
            .addQueryParameter("secret", secret)
            .addQueryParameter("js_code", code)
            .addQueryParameter("grant_type", WeChatApi.JS_CODE_2_SESSION.grantType)
            .build();
    Request request = new Request.Builder()
            .url(url)
            .get().build();
    ResponseInfo response = HttpClient.DEFAULT.syncCallOrThrow(request);
    WeChatSessionVO sessionVO = JsonUtils.fromOrException(response.body, WeChatSessionVO.class);
    checkResponse(sessionVO);
    return sessionVO;
  }


  @Override
  public WeChatAccessTokenVO getAccessToken() throws ExecutionException {
    return cache.get("access_token");
  }

  private WeChatAccessTokenVO obtainAccessToken() {
    HttpUrl url = HttpUrl.parse(WeChatApi.GET_ACCESS_TOKEN.url).newBuilder()
            .addQueryParameter("appid", appId)
            .addQueryParameter("secret", secret)
            .addQueryParameter("grant_type", WeChatApi.GET_ACCESS_TOKEN.grantType)
            .build();
    Request request = new Request.Builder()
            .url(url)
            .get().build();
    ResponseInfo response = HttpClient.DEFAULT.syncCallOrThrow(request);
    WeChatAccessTokenVO accessTokenVO = JsonUtils.fromOrException(response.body, WeChatAccessTokenVO.class);
    checkResponse(accessTokenVO);
    return accessTokenVO;
  }

  private void checkResponse(BaseWeChatMsgVO msgVO) {
    if (msgVO.isError()) {
      throw JobException.error("getSessionFromWeChat error,msg is {}", JsonUtils.toString(msgVO));
    }
  }
}
