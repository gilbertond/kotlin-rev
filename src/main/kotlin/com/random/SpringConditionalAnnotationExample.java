package com.random;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
 
public class SpringConditionalAnnotationExample {
 
    public static void main(String[] args) {
        loadContextAndVerifyBeans(BeanA.class, BeanB.class);
        loadContextAndVerifyBeans(BeanB.class);
        loadContextAndVerifyBeans(BeanB.class, BeanA.class);
    }
 
    private static void loadContextAndVerifyBeans(Class<?>...classToRegister) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        try {
            ctx.register(classToRegister);
            ctx.refresh();
            System.out.println("Has beanA? " + ctx.containsBean("beanA"));
            System.out.println("Has beanB? " + ctx.containsBean("beanB"));
        } finally {
            ctx.close();
        }
    }
 
    @Configuration("beanA")
    static class BeanA {
    }
 
    @Configuration("beanB")
    @Conditional(IfBeanAExistsCondition.class)        // Create bean B if bean A exists!!
    static class BeanB {
    }
}