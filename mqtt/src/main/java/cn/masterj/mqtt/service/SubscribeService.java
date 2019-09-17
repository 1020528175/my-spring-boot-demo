package cn.masterj.mqtt.service;

import cn.masterj.mqtt.util.MqttProp;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author master
 * @create 2019-09-17 11:02
 */
@Service
public class SubscribeService {

    @Autowired
    private MqttProp mqttProp;

    private MqttClient mqttClient;
    /**
     * mqtt连接配置类
     */
    private MqttConnectOptions options;

    public void start() {
        try {
            System.out.println("mqttProp = " + mqttProp);
            // 在服务开始时new一个mqttClient实例，客户端ID为clientId,一定要是唯一的，如果两个client的id是一样的，就会互相冲下线，只会有一个在线的，
            // 第三个参数说明是持久化客户端，如果是null则是非持久化
            mqttClient = new MqttClient(mqttProp.getBrokerUrl(), mqttProp.getClientId(), new MemoryPersistence());

            // MQTT的连接设置
            options = new MqttConnectOptions();
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
            // 设置回调  回调类的说明看后面
            mqttClient.setCallback(new PushCallback(mqttClient, options));
            MqttTopic topic = mqttClient.getTopic(mqttProp.getTopic());
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill(topic.getName() + "-close", "close subscribe".getBytes(), 2, true);
            //mqtt客户端连接服务器
            mqttClient.connect(options);

            //mqtt客户端订阅主题
            //在mqtt中用QoS来标识服务质量
            //QoS=0时，报文最多发送一次，有可能丢失
            //QoS=1时，报文至少发送一次，有可能重复
            //QoS=2时，报文只发送一次，并且确保消息只到达一次。
            int[] Qos = {1};
            String[] topic1 = {mqttProp.getTopic()};
            mqttClient.subscribe(topic1, Qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}
