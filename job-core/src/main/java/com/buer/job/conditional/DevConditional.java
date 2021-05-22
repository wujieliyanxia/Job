package com.buer.job.conditional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DevConditional implements Condition {

  @Override
  public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
    String[] activeProfiles = conditionContext.getEnvironment().getActiveProfiles();
    for (String profile : activeProfiles) {
      if (StringUtils.equalsIgnoreCase(profile, "dev")) {
        return true;
      }
    }
    return false;
  }
}
