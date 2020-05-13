package com.xingplanet.atomrpc.rpc.server;

import com.xingplanet.atomrpc.rpc.protocol.RpcProtocol;

/**
 * @author wangjin
 */
public interface RpcServerExecutor {

    /**
     * 执行方法
     *
     * @param request 情报包体
     * @return 响应包体
     * @throws Exception 异常
     */
    RpcProtocol execute(RpcProtocol request) throws Exception;
}
