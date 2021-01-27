package com.buer.job.utils.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * HTTP 服务接口类
 */
@Deprecated
public class HttpClientCommand {
  public static Response doExecute(OkHttpClient client, Request request) throws IOException {
    // TODO(JIEWU,T0000)未来在这加上监控，统一监控下第三方请求的耗时
    return client.newCall(request).execute();
  }
}
