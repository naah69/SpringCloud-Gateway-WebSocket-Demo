package com.naah.bullet;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketAutoConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/bullet")     //开启/bullet端点
                .setAllowedOrigins("*")             //允许跨域访问
                .withSockJS();                      //使用sockJS
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //订阅Broker名称
        //registry.enableSimpleBroker("/topic","/user");
        registry.enableSimpleBroker("/toAll");
        //全局使用的订阅前缀（客户端订阅路径上会体现出来）
        //registry.setApplicationDestinationPrefixes("/app/");
        //
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        //registry.setUserDestinationPrefix("/user/");
    }
}