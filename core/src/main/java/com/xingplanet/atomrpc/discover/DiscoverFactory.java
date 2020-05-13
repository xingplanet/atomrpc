package com.xingplanet.atomrpc.discover;

import com.xingplanet.atomrpc.config.annotation.RpcInterface;
import com.xingplanet.atomrpc.discover.DiscoverService;
import com.xingplanet.atomrpc.register.zookeeper.ZookeeperRegister;
import com.xingplanet.atomrpc.rpc.proxy.jdk.JdkProxyFactory;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * @author wangjin
 */
@Component
@ConditionalOnBean(value = DiscoverService.class)
public class DiscoverFactory implements ApplicationContextAware, InitializingBean {

    @Autowired
    private DiscoverService discoverService;

    private ApplicationContext applicationContext;
    @Override
    public void afterPropertiesSet() throws Exception {
        String[] packageNameArray = discoverService.getPackageNameArray();
        for (String packageName : packageNameArray) {
            Reflections reflections = new Reflections(packageName);
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
            Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(RpcInterface.class);
            if (!typesAnnotatedWith.isEmpty()) {
                for (Class<?> clazz : typesAnnotatedWith) {
                    beanFactory.registerSingleton(clazz.getSimpleName(), JdkProxyFactory.getProxy(clazz));
                }
            }
        }
        System.out.println("DiscoverFactory afterPropertiesSet");

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
