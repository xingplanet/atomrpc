package com.xingplanet.atomrpc.rpc.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjin
 */
public abstract class AbstractRpcServerExecutor implements RpcServerExecutor{

    protected static final Map<String, Object> INSTANCE_MAP = new HashMap<>(16);

    public static void add(String key, Object instance) {
        INSTANCE_MAP.put(key, instance);
    }

}
