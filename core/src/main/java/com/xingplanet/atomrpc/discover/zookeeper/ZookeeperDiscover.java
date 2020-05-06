package com.xingplanet.atomrpc.discover.zookeeper;

import com.xingplanet.atomrpc.discover.AbstractDiscover;
import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjin
 */
public class ZookeeperDiscover extends AbstractDiscover {

    private final static String ZK_ROOT = "/rpc";

    private ZkClient zkClient;

    public ZookeeperDiscover(String zkAddr, int timeout) {
        this.serviceMap = new ConcurrentHashMap<>();
        this.zkClient = new ZkClient(zkAddr, timeout);

    }

    @Override
    public void subscribe(String serviceName) {
        String path =  ZK_ROOT + "/" + serviceName;
//        zkClient.subscribeDataChanges(path, );
    }
}
