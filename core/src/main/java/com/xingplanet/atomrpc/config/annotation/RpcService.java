package com.xingplanet.atomrpc.config.annotation;


import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface RpcService {
    @AliasFor(annotation = Component.class)
    String value() default "";

    /**
     * 实现的接口
     */
    Class<?> interfaceName();

    String name() default "";

    String version() default "";

}
