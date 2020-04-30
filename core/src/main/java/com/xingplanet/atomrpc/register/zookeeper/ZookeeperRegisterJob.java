package com.xingplanet.atomrpc.register.zookeeper;

import com.xingplanet.atomrpc.util.SpringBeanFactory;

import java.util.List;

/**
 * @author wangjin
 */
public class ZookeeperRegisterJob implements Runnable {

    private List<String> serviceName;

    private ZookeeperRegister zookeeperRegister;

    ZookeeperRegisterJob(List<String> serviceName) {
        this.serviceName = serviceName;
        zookeeperRegister = SpringBeanFactory.getBean(ZookeeperRegister.class);
    }

    @Override
    public void run() {
        zookeeperRegister.register(serviceName);
    }
}
