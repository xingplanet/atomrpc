package com.xingplanet.atomrpc.register.zookeeper;

import com.xingplanet.atomrpc.config.annotation.RestfulService;
import org.springframework.beans.BeansException;
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
public class ZookeeperRegisterFactory implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RestfulService.class);
        List<String> serviceList = new ArrayList<>();
        for (Object service : serviceMap.values()) {
            serviceList.add(service.getClass().getName());
        }
        Thread thread =new Thread(new ZookeeperRegisterJob(serviceList));
        thread.setName("register zookeeper");
        thread.start();
    }
}
