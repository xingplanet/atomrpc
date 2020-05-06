package com.xingplanet.atomrpc.register.zookeeper;

import com.xingplanet.atomrpc.config.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangjin
 */
@Component
@ConditionalOnBean(value = ZookeeperRegister.class)
public class ZookeeperRegisterFactory implements ApplicationContextAware, InitializingBean {

    @Autowired
    private ZookeeperRegister zookeeperRegister;

    private ApplicationContext applicationContext;
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("ZookeeperRegisterFactory afterPropertiesSet");
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        List<String> serviceList = new ArrayList<>();
        for (Object service : serviceMap.values()) {
            serviceList.add(service.getClass().getName());
        }
        Thread thread = new Thread(new ZookeeperRegisterJob(serviceList, zookeeperRegister));
        thread.setName("register zookeeper");
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ZookeeperRegisterFactory");
        this.applicationContext = applicationContext;
    }
}
