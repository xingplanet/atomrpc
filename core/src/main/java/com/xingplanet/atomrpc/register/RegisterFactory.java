package com.xingplanet.atomrpc.register;

import com.xingplanet.atomrpc.config.annotation.RpcService;
import com.xingplanet.atomrpc.rpc.server.AbstractRpcServerExecutor;
import com.xingplanet.atomrpc.rpc.server.RpcServer;
import com.xingplanet.atomrpc.rpc.server.netty.NettyRpcServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangjin
 */
@Component
@ConditionalOnBean(value = RegisterService.class)
public class RegisterFactory implements ApplicationContextAware, InitializingBean, Closeable {

    @Autowired
    private RegisterService register;

    private ApplicationContext applicationContext;

    private RpcServer rpcServer;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("RegisterFactory afterPropertiesSet");
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        List<String> serviceList = new ArrayList<>();
        for (Object service : serviceMap.values()) {
            String interfaceName = service.getClass().getAnnotation(RpcService.class).interfaceName().getName();
            serviceList.add(interfaceName);
            // 服务端注册映射
            AbstractRpcServerExecutor.add(interfaceName, service);
        }
        Thread thread = new Thread(new RegisterJob(serviceList, register));
        thread.setName("register service");
        thread.start();
        // 开启服务监听
        this.rpcServer = new NettyRpcServer(register.getPort());
        this.rpcServer.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("RegisterFactory");
        this.applicationContext = applicationContext;
    }

    @Override
    public void close() {
        this.rpcServer.destroy();
    }

}
