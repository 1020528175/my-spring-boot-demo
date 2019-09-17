package cn.masterj.mqtt.test;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MQTTPublish implements MqttCallback {


    public static final String HOST = "tcp://mqtt.p2hp.com:1883";

    public static final String TOPIC = "gl";
    private static final String clientid = "publisher";

    private static final String str = "法国恢复和";
    private static MqttClient client;

    private MqttTopic topic;
    private String userName = "admin";
    private String passWord = "password";

    private MqttMessage message;
    MqttConnectOptions options = new MqttConnectOptions();


    private ScheduledExecutorService scheduler;


    public void startReconnect() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                if (!client.isConnected()) {
                    try {
                        client.connect(options);
                        System.out.println("重连成功");
                    } catch (MqttSecurityException e) {
                        e.printStackTrace();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    public MQTTPublish() throws MqttException {
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
    }

    private void connect() {
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(this);

            client.connect(options);
            topic = client.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(MqttMessage message) throws MqttPersistenceException, MqttException {
        MqttDeliveryToken token = topic.publish(message);
//        token.waitForCompletion();
        System.out.println("Token is complete:" + token.isComplete());
    }

    public static void main(String[] args) throws MqttException {
        MQTTPublish mqttpub = new MQTTPublish();
        mqttpub.message = new MqttMessage();
        //设置qos，决定消息到达次数，如果是1，消息重复的不会到达
        mqttpub.message.setQos(2);
        mqttpub.message.setRetained(true);
//        mqttpub.message.setPayload(str.getBytes());
        mqttpub.message.setPayload("1101".getBytes());
        mqttpub.publish(mqttpub.message);
//        System.out.println("Ratained state:" + mqttpub.message.isRetained());
//
//        client.disconnect();
//        System.out.println("Disconnected");
//        System.exit(0);
    }

    @Override
    public void connectionLost(Throwable arg0) {
        startReconnect();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        try {
            System.out.println("deliveryComplete---------" + token.isComplete());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
        // TODO Auto-generated method stub

    }
}
