package com.xingplanet.atomrpc.discover.zookeeper;

import com.xingplanet.atomrpc.discover.AbstractDiscover;
import com.xingplanet.atomrpc.discover.DiscoverDictionary;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @author wangjin
 */
public class ZookeeperDiscover extends AbstractDiscover {

    private final static String ZK_ROOT = "/rpc";

    private ZkClient zkClient;

    public ZookeeperDiscover(String zkAddr, int timeout, String[] packageNameArray) {
        super(packageNameArray);
        this.zkClient = new ZkClient(zkAddr, timeout);
    }

    @Override
    public void subscribe(String serviceName) {
        String path =  ZK_ROOT + "/" + serviceName;
        List<String> subcirbeNode = zkClient.subscribeChildChanges(path, new IZkChildListener() {

            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                DiscoverDictionary.put(serviceName, currentChilds);
            }
        });
        DiscoverDictionary.put(serviceName, subcirbeNode);
    }
}
