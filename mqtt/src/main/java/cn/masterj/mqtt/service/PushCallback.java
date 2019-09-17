package cn.masterj.mqtt.service;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author master
 * @create 2019-09-17 11:28
 */
@Slf4j
public class PushCallback implements MqttCallback {
    private MqttClient client;
    private MqttConnectOptions options;

    public PushCallback(MqttClient client, MqttConnectOptions options) {
        this.client = client;
        this.options = options;
    }

    /**
     * 连接断开时的回调方法，可以在这里重新连接
     *
     * @param cause
     */
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("连接断开时的回调方法，可以在这里重新连接");
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if (!client.isConnected()) {
                try {
                    client.connect(options);
                    log.info("重连成功 => " + LocalDateTime.now());
                } catch (MqttSecurityException e) {
                    e.printStackTrace();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }, 0 * 1000, 10 * 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //有新消息到达时的回调方法
        System.out.println("到达===topic = " + topic);
        System.out.println("到达===message = " + message);
        System.out.println("==============================================");
    }

    /**
     * 成功发布某一消息后的回调方法
     *
     * @param token
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("成功发布某一消息后的回调方法");
    }
}