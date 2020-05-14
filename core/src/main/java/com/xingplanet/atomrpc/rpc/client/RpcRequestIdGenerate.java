package com.xingplanet.atomrpc.rpc.client;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wangjin
 */
public class RpcRequestIdGenerate {

    private static final AtomicLong requestId = new AtomicLong(0);

    public static long getNewRequestId() {
        return requestId.addAndGet(1);
    }
}
