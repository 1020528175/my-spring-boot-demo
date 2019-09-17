package cn.masterj.mqtt.service;

import cn.masterj.mqtt.util.MqttProp;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author master
 * @create 2019-09-17 11:02
 */
@Service
@Slf4j
public class PublishService {

    @Autowired
    private MqttProp mqttProp;

    public void send() throws Exception {
        System.out.println("mqttProp = " + mqttProp);
        //在服务开始时new一个mqttClient实例，客户端ID为clientId，第三个参数说明是持久化客户端，如果是null则是非持久化
        MqttClient mqttClient = new MqttClient(mqttProp.getBrokerUrl(), mqttProp.getClientId() + "123", new MemoryPersistence());

        // MQTT的连接设置
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        //换而言之，设置为false时可以客户端可以接受离线消息
        options.setCleanSession(false);
        // 设置连接的用户名
        options.setUserName(mqttProp.getUserName());
        // 设置连接的密码
        options.setPassword(mqttProp.getPassWord().toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        //mqtt客户端连接服务器
        mqttClient.connect(options);

        mqttClient.publish(mqttProp.getTopic(), new MqttMessage("你好".getBytes()));
        log.info("-------------------发送成功-------------------");
    }

}
