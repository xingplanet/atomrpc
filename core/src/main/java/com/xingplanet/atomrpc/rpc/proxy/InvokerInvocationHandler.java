package com.xingplanet.atomrpc.rpc.proxy;

import com.xingplanet.atomrpc.discover.DiscoverDictionary;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author wangjin
 */
public class InvokerInvocationHandler<T> implements InvocationHandler {

    private Class<T> clazz;

    public InvokerInvocationHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }

        Set<String> candidates = DiscoverDictionary.get(clazz.getName());
        // TODO 负载均衡
        String ip = candidates.iterator().next();
        String host = ip.split(":")[0];
        String port = ip.split(":")[1];
        return null;
    }
}
