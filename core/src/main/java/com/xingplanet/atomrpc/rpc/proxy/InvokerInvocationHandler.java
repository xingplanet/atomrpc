package com.xingplanet.atomrpc.rpc.proxy;

import com.xingplanet.atomrpc.discover.DiscoverDictionary;
import com.xingplanet.atomrpc.rpc.client.RpcClient;
import com.xingplanet.atomrpc.rpc.client.RpcClientFactory;
import com.xingplanet.atomrpc.rpc.client.netty.NettyRpcClient;

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

        RpcClient rpcClient = RpcClientFactory.getClient(clazz.getName());
        return rpcClient.execute(method, args);
    }
}
