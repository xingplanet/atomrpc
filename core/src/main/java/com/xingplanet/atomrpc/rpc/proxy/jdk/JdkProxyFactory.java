package com.xingplanet.atomrpc.rpc.proxy.jdk;

import com.xingplanet.atomrpc.rpc.proxy.InvokerInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * @author wangjin
 */
public class JdkProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                new InvokerInvocationHandler(clazz));
    }
}
