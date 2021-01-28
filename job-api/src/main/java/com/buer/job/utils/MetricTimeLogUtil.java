package com.buer.job.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jpdu on 2018/12/17.
 */
public class MetricTimeLogUtil {
  private static final Logger METRIC_TIME_LOGGER = LoggerFactory.getLogger("metricTime");

  public static void logNormal(HttpServletRequest request, Long costInMills) {
    String pattern = String.valueOf(request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE));

    String path = request.getServletPath();
    path = StringUtils.equals(path, pattern) ? null : path;
    MetricTimeLogUtil.logNormal(
        request.getMethod(),
        pattern,
        costInMills,
        path
    );
  }

  private static void logNormal(String method, String pathPattern, Long costInMills, String path) {
    path = StringUtils.equals(path, pathPattern) ? null : path;
    METRIC_TIME_LOGGER.info(
        "{} | {} | {} | none | {}",
        method,
        pathPattern,
        costInMills,
        path
    );
  }
}
