package com.xingplanet.atomrpc.register;

import java.util.List;

/**
 * 服务注册
 *
 * @author wangjin
 */
public interface RegisterService {

    /**
     * 服务注册接口
     *
     * @param serviceNamList 服务名称列表
     */
    void register(List<String> serviceNamList);

}
