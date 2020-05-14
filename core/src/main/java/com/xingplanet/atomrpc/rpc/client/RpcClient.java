package com.xingplanet.atomrpc.rpc.client;

import java.lang.reflect.Method;

/**
 * @author wangjin
 */
public interface RpcClient {

    /**
     * 执行client
     *
     * @param method 方法
     * @param args   参数
     * @return 返回对象
     * @throws Exception 异常
     */
    Object execute(Method method, Object[] args) throws Exception;

    /**
     * 启动
     * @throws Exception 异常
     */
    void start() throws Exception;

    /**
     * 销毁
     */
    void destroy();
}
