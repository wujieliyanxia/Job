package com.buer.job.utils.http;

import com.buer.job.exception.JobException;
import com.buer.job.utils.Clock;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.TimeUnit;

/**
 * HTTP 服务接口类
 */
@Slf4j
public class HttpClient {
  public static final String CONTENT_TYPE = "Content-Type";
  public static final MediaType JSON_MEDIA_UTF8 = MediaType.parse("application/json; charset=utf-8");
  public static final MediaType JSON_MEDIA = MediaType.parse("application/json");
  public static final MediaType FORM_MEDIA_UTF8 = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
  public static final MediaType FORM_MEDIA = MediaType.parse("application/x-www-form-urlencoded");
  public static final MediaType IMAGE_JPEG_MEDIA = MediaType.parse("image/jpeg");
  public static final MediaType IMAGE_JPG_MEDIA = MediaType.parse("image/jpg");
  public static final MediaType PDF_MEDIA = MediaType.parse("application/pdf");
  public static final MediaType HTML_MEDIA = MediaType.parse("text/html");
  public static final MediaType HTML_MEDIA_UTF8 = MediaType.parse("text/html; charset=utf-8");
  public static final MediaType XML_MEDIA = MediaType.parse("application/xml");
  public static final MediaType MULTIPART_FORM_MEDIA = MediaType.parse("multipart/form-data");

  private OkHttpClient client;

  public static final HttpClient DEFAULT = new HttpClient(15 * 1000, 15 * 1000);

  public HttpClient(OkHttpClient client) {
    this.client = client;
  }

  public HttpClient(long connectionTimeout, long readTimeout) {
    this.client = new OkHttpClient.Builder()
        .connectTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
        .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
        .build();
  }

  public OkHttpClient getClient() {
    return client;
  }

  public ResponseInfo syncCallOrNull(Request request) {
    try {
      return syncCallOrThrow(request);
    } catch (Exception e) {
      log.warn("Error when sending request. url:{}", request.url(), e);
    }
    return null;
  }

  public ResponseInfo syncCallOrThrow(Request request) {
    Pair<Integer, String> pair = send(request, response -> response.body().string());
    return new ResponseInfo(pair.getLeft(), pair.getRight());
  }

  public ResponseInfo syncGetBytesOrNull(Request request) {
    try {
      return syncGetBytesOrThrow(request);
    } catch (Exception e) {
      log.warn("Error when sending request. url:{}", request.url(), e);
    }
    return null;
  }

  public ResponseInfo syncGetBytesOrThrow(Request request) {
    Pair<Integer, byte[]> pair = send(request, response -> response.body().bytes());
    return new ResponseInfo(pair.getLeft(), pair.getRight());
  }

  private <T> Pair<Integer, T> send(Request request, ResponseConverter<T> extractData) {
    long st = Clock.now();
    Response resp = null;
    try {
      log.info("request start. request: {}", request);
      resp = HttpClientCommand.doExecute(client, request);
      return Pair.of(resp.code(), extractData.apply(resp));
    } catch (Exception e) {
      throw JobException.error("Error when sending request, request: {}", request, e);
    } finally {
      releaseBody(resp);
      log.info("request end. cost: {}ms, response: {}", Clock.now() - st, resp);
    }
  }

  private void releaseBody(Response response) {
    try {
      if (response == null) {
        return;
      }
      ResponseBody body = response.body();
      if (body != null) {
        body.close();
      }
    } catch (Exception e) {
      // ignore
    }
  }

  public interface ResponseConverter<R> {
    default R apply(Response response) {
      try {
        return convert(response);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    R convert(Response response) throws Exception;
  }
}
