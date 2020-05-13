package com.xingplanet.atomrpc.register.zookeeper;

import com.xingplanet.atomrpc.register.AbstractRegister;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * TODO 失败重试
 * 服务提供方，生成ZookeeperRegister的bean
 *
 * @author wangjin
 */
public class ZookeeperRegister extends AbstractRegister {

    private final static String ZK_ROOT = "/rpc";

    private ZkClient zkClient;

    public ZookeeperRegister(String zkAddr, int timeout, int port) {
        super(port);
//        this.zkClient = new ZkClient(zkAddr, timeout);
        this.zkClient = null;
    }


    @Override
    public void register(List<String> serviceNameList) {
        createRootNode();

        serviceNameList.forEach(item -> {
            String path = ZK_ROOT + "/" + item + "/" + serviceAddress;
            createNode(path);
        });

    }

    private void createRootNode() {
        boolean exists = zkClient.exists(ZK_ROOT);
        if (exists) {
            return;
        }

        zkClient.createPersistent(ZK_ROOT);
    }

    private void createNode(String path) {
        zkClient.createEphemeral(path);
        logger.info("register zookeeper success, path: {}.", path);
    }
}
