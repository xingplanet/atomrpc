package com.xingplanet.atomrpc.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangjin
 */
public abstract class AbstractRegister implements RegisterService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String serviceAddress;

    protected AbstractRegister(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

}
