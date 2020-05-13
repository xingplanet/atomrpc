package com.xingplanet.atomrpc.register;

import com.xingplanet.atomrpc.rpc.server.netty.RpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wangjin
 */
public class RegisterJob implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServer.class);

    private final List<String> serviceName;

    private final RegisterService register;

    RegisterJob(List<String> serviceName, RegisterService register) {
        this.serviceName = serviceName;
        this.register = register;
    }

    @Override
    public void run() {
        try {
            if (register != null) {
                register.register(serviceName);
            } else {
                System.out.println("zookeeperRegister or zkClient is null.");
            }
        } catch (Exception e) {
            LOGGER.error("Register error: {}.", e.getMessage(), e);
        }
    }
}
