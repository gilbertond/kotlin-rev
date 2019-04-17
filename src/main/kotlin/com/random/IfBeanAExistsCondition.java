package com.random;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class IfBeanAExistsCondition implements Condition {

    public boolean matches(
            ConditionContext context,
            AnnotatedTypeMetadata metadata
    ) {
        return context.getBeanFactory().containsBeanDefinition("beanA");
    }
}