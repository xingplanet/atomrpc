package com.xingplanet.atomrpc.rpc.message;

/**
 * @author wangjin
 */
public class RpcResponseMessage {

    private long requestId;

    private Throwable throwable = null;

    private Object result;

    /**
     * 创建响应的包体
     *
     * @param requestId 唯一请求号
     * @param result    如果响应正常，则返回结果
     * @param throwable 异常
     */
    public RpcResponseMessage(long requestId, Object result, Throwable throwable) {
        this.requestId = requestId;
        this.result = result;
        this.throwable = throwable;
    }

    /**
     * 创建响应的包体
     *
     * @param requestId 唯一请求号
     * @param throwable 异常
     */
    public RpcResponseMessage(long requestId, Throwable throwable) {
        this(requestId, null, throwable);
    }

    /**
     * 创建响应的包体
     *
     * @param requestId 唯一请求号
     * @param result    如果响应正常，则返回结果
     */
    public RpcResponseMessage(long requestId, Object result) {
        this(requestId, result, null);
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
