package com.personalize.knowledge.configuration;

import com.vesoft.nebula.client.graph.SessionPool;
import com.vesoft.nebula.client.graph.SessionPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

@Configuration
public class NebulaConfig {
    @Value("${nebula.hosts}")
    private String hosts;
    @Value("${nebula.port}")
    private int port;
    @Value("${nebula.spaceName}")
    private String spaceName;
    @Value("${nebula.username}")
    private String user;
    @Value("${nebula.password}")
    private String password;

    @Bean
    public SessionPool sessionPool() {
        List<HostAddress> addresses = Arrays.asList(new HostAddress(hosts, port));
        SessionPoolConfig sessionPoolConfig =
                new SessionPoolConfig(addresses, spaceName, user, password)
                        .setMaxSessionSize(10)
                        .setMinSessionSize(10)
                        .setReconnect(true)
                        .setWaitTime(100)
                        .setRetryTimes(3)
                        .setIntervalTime(100);
        return new SessionPool(sessionPoolConfig);
    }
    @PreDestroy
    public void cleanUp(){
        sessionPool().close();
    }
}
