package com.xingplanet.atomrpc.register.zookeeper;

import java.util.List;

/**
 * @author wangjin
 */
public class ZookeeperRegisterJob implements Runnable {

    private List<String> serviceName;

    private ZookeeperRegister zookeeperRegister;

    ZookeeperRegisterJob(List<String> serviceName, ZookeeperRegister zookeeperRegister) {
        this.serviceName = serviceName;
        this.zookeeperRegister = zookeeperRegister;
    }

    @Override
    public void run() {
        if (zookeeperRegister != null) {
            zookeeperRegister.register(serviceName);
        } else {
            System.out.println("zookeeperRegister or zkClient is null.");
        }
    }
}
