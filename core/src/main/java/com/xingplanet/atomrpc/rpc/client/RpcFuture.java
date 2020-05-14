package com.xingplanet.atomrpc.rpc.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjin
 */
public class RpcFuture {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private Object data;

    public Object get() throws InterruptedException {
        countDownLatch.await(2000, TimeUnit.MILLISECONDS);
        return data;
    }

    public void done(Object data) {
        this.data = data;
        countDownLatch.countDown();
    }

}
