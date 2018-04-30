package com.naah.eurekasever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaseverApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaseverApplication.class, args);
    }
}
