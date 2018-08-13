package com.naah.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringCloudApplication
//@EnableRedisHttpSession
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
