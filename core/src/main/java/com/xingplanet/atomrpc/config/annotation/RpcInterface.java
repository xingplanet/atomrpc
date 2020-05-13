package com.xingplanet.atomrpc.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RPC接口的注解，调用方引入rpc jar时，会自动生成代理类
 * @author wangjin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcInterface {
}
