package com.xingplanet.atomrpc.rpc.server;

/**
 * @author wangjin
 */
public interface RpcServer {
    /**
     * 启动
     */
    void start() throws Exception;

    /**
     * 销毁
     */
    void destroy();
}
