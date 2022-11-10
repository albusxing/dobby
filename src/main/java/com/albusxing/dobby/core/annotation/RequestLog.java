package com.albusxing.dobby.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liguoqing
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLog {

    /**
     * 功能
     * @return
     */
    String func() default "";

    /**
     * 是否持久化到数据库
     * @return
     */
    boolean durable() default false;

    /**
     * 是否控制台打印
     * @return
     */
    boolean console() default false;


}
