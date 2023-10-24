package com.example.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author czq
 * @date 2023/10/18 11:14
 * @Description:
 */
public class UserServiceCondition implements Condition, ConfigurationCondition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return true;
    }

    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return null;
    }
}
