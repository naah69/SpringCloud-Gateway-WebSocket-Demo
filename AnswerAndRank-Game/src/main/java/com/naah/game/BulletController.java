package com.naah.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class BulletController {

    private static final Logger logger=LoggerFactory.getLogger(BulletController.class);
    @MessageMapping("/chat")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/toAll/bulletScreen")
    public String say(String clientMessage) {
        //方法用于广播测试
        String result="123";
        if (clientMessage!=null){
//            result=clientMessage.getUsername()+":"+clientMessage.getMessage();
        }else{
            result="null";
        }
        return result;
    }

    //注入SimpMessagingTemplate 用于点对点消息发送
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

}