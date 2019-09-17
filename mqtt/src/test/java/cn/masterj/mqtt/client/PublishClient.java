package cn.masterj.mqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

/**
 * @author master
 * @create 2019-09-16 16:31
 */
public class PublishClient implements MqttCallback {

    public static void main(String[] args) throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost("127.0.0.1", 1883);
//        mqtt.setHost("tcp://localhost:1883")

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();
        connection.publish("gl", "Hello2".getBytes(), QoS.AT_LEAST_ONCE, false);



    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
