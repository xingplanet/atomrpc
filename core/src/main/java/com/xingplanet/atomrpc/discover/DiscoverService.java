package com.xingplanet.atomrpc.discover;

/**
 * @author wangjin
 */
public interface DiscoverService {

    /**
     * 服务订阅
     *
     * @param serviceName 服务名
     */
    void subscribe(String serviceName);

    /**
     * 获取包名
     *
     * @return 包名数组
     */
    String[] getPackageNameArray();

}
