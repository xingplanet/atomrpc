package com.xingplanet.atomrpc;

import com.xingplanet.atomrpc.register.zookeeper.ZookeeperRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangjin
 */
@Configuration
public class Test {

    @Bean
    public ZookeeperRegister zookeeperRegister() {
        System.out.println("ZookeeperRegister");
        return new ZookeeperRegister("", 5000, "");
    }
}
