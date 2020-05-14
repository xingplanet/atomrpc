package com.xingplanet.atomrpc.rpc.client;

import com.xingplanet.atomrpc.discover.DiscoverDictionary;
import com.xingplanet.atomrpc.rpc.client.netty.NettyRpcClient;
import com.xingplanet.atomrpc.rpc.constant.OperationCode;
import com.xingplanet.atomrpc.rpc.message.RpcRequestMessage;
import com.xingplanet.atomrpc.rpc.protocol.RpcProtocol;
import com.xingplanet.atomrpc.rpc.protocol.RpcProtocolBuilder;
import com.xingplanet.atomrpc.rpc.transcoder.SingleTranscoder;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author wangjin
 */
public class RpcClientFactory {

    private static final ConcurrentMap<String, RpcClient> CLIENT_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Long, RpcFuture> FUTURE_MAP = new ConcurrentHashMap<>();

    public static RpcClient getClient(String serviceName) throws Exception {

        Set<String> candidates = DiscoverDictionary.get(serviceName);
        if (candidates == null || candidates.isEmpty()) {
            throw new Exception("this rpc service unavailable");
        }
        // TODO 负载均衡
        String ip = candidates.iterator().next();
        RpcClient client = CLIENT_MAP.get(ip);
        if (client == null) {
            synchronized (RpcClientFactory.class) {
                client = new NettyRpcClient(ip);
                CLIENT_MAP.put(ip, client);
            }
        }
        return client;
    }

    public static RpcProtocol buildProtocol(Method method, Object[] args) throws Exception {
        RpcRequestMessage request = new RpcRequestMessage();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        return RpcProtocolBuilder.buildRequest(RpcRequestIdGenerate.getNewRequestId(), OperationCode.HEARTBEAT_REQUEST, request, new SingleTranscoder());
    }

    public static RpcFuture getFuture(long requestId) {
        return FUTURE_MAP.get(requestId);
    }

    public static void setFuture(long requestId, RpcFuture rpcFuture) {
        FUTURE_MAP.put(requestId, rpcFuture);
    }

    public static void removeFuture(long requestId) {
        FUTURE_MAP.remove(requestId);
    }

}
