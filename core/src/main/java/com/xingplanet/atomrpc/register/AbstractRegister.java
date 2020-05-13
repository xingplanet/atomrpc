package com.xingplanet.atomrpc.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author wangjin
 */
public abstract class AbstractRegister implements RegisterService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String serviceAddress;
    protected int port;

    protected AbstractRegister(int port) {
        try {
            String addr = InetAddress.getLocalHost().getHostAddress();
            this.serviceAddress = addr + ":" + port;
            this.port = port;
        } catch (UnknownHostException e) {
            logger.error("get host error.", e);
        }

    }

    @Override
    public String getServiceAddress() {
        return this.serviceAddress;
    }

    @Override
    public int getPort() {
        return this.port;
    }

}
