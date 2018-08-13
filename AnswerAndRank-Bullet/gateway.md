# Spring Cloud Gateway 转发 Spring WebSocket
在经过了两天的刻苦钻研，

在经过了google,stackoverflow，github轮番查找无果，

终于，一个夜里，在肯德基的我调试通过...
***

我们在这里模拟一个广播弹幕的websocket

gateway通过eureka注册中心拉取服务进行转发websocket

## 搭建 Spring WebSocket

#### pom.xml websocket maven依赖
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-websocket</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
```

#### application.yml 配置文件
```
spring:
  application:
    name: bullet
server:
  port: 5678
eureka:
  client:
     serviceUrl:
      defaultZone: http://localhost:1025/eureka/
```

#### BulletApplication websocket启动程序
```
@SpringBootApplication
public class BulletApplication {

    public static void main(String[] args) {
        SpringApplication.run(BulletApplication.class, args);
    }
}
```

#### WebSocketAutoConfig websocket配置类
```
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketAutoConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/bullet")         //开启/bullet端点
                .setAllowedOrigins("*")         //允许跨域访问
                .withSockJS();                  //使用sockJS
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/toAll");  //订阅Broker名称
    }
}
```
#### BulletMessageVO类
使用lombok的@Getter和@Setter注解来自动生成get、set方法
```
@Getter
@Setter
public class BulletMessageVO {
    String username;
    String message;
}
```
#### BulletController websocket控制器
```
@Controller
public class BulletController {

    @MessageMapping("/chat")
    @SendTo("/toAll/bulletScreen")             //SendTo 发送至 Broker 下的指定订阅路径
    public String say(BulletMessageVO clientMessage) {
        String result=null;
        if (clientMessage!=null){
            result=clientMessage.getUsername()+":"+clientMessage.getMessage();
        }
        return result;
    }
}
```

## 搭建 Spring Cloud Gateway
#### pom.xml gateway网关maven依赖
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
        </dependency>
```

#### application.yml gateway网关配置文件
```
spring:
  application:
    name: gateway
  gateway:
        routes:
        - id: bulletscreen
          # 重点！/info必须使用http进行转发，lb代表从注册中心获取服务
          uri: lb://bullet
          predicates:
          # 重点！转发该路径！
          - Path=/bullet/info/**
        - id: bulletscreen
          # 重点！lb:ws://代表从注册中心获取服务，并且转发协议为websocket，这种格式怀疑人生！
          uri: lb:ws://bullet
          predicates:
          # 转发/bullet端点下的所有路径
          - Path=/bullet/**
server:
  port: 8888
eureka:
  client:
     serviceUrl:
      defaultZone: http://localhost:1025/eureka/
```

#### GatewayApplication gateway启动类
```
@SpringCloudApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
```

## 编写html
这个是从网上找来的html代码
可以保存到本地文件，不需要放入服务里面
```
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Spring Boot WebSocket+广播式</title>
</head>
<body onload="disconnect()">
    <noscript>
        <h2 style="color:#ff0000">貌似你的浏览器不支持websocket</h2>
    </noscript>
    <div>
        <div>
            <button id="connect" onclick="connect()">连接</button>
            <button id="disconnect"  onclick="disconnect();">断开连接</button>
        </div>
        <div id="conversationDiv">
            <label>输入你的名字</label> <input type="text" id="name" />
            <br>
            <label>输入消息</label> <input type="text" id="messgae" />
            <button id="send" onclick="send();">发送</button>
            <p id="response"></p>
        </div>
    </div>
    <script src="https://cdn.bootcss.com/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdn.bootcss.com/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript">
        var stompClient = null;
        //gateway网关的地址
        var host="http://127.0.0.1:8888";
        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
            $('#response').html();
        }

        function connect() {
            //地址+端点路径，构建websocket链接地址
            var socket = new SockJS(host+'/bullet');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected:' + frame);
                //监听的路径以及回调
                stompClient.subscribe('/toAll/bulletScreen', function(response) {
                    showResponse(response.body);
                });

            });
        }


        function disconnect() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }

        function send() {
            var name = $('#name').val();
            var message = $('#messgae').val();
            //发送消息的路径
            stompClient.send("/chat", {}, JSON.stringify({username:name,message:message}));
        }

        function showResponse(message) {
            var response = $('#response');
            response.html(message);
        }
    </script>
</body>
</html>
```
## 启动程序
1. 启动Eureka 服务端，开启注册中心
2. 启动Bullet WebSocket程序
3. 启动GateWay网关

## 测试程序
1. 开启多个html页面，并打开控制台

![](http://east1naah.dazhiyy.cn/img/20180502134411.png)

2. 在多个页面中点击连接按钮，观察控制台是否连接成功

![](http://east1naah.dazhiyy.cn/img/20180502134547.png)

3. 输入名字和消息，观察是否成功进行广播

![](http://east1naah.dazhiyy.cn/img/20180502134824.png)