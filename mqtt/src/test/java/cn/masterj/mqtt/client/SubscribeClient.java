package cn.masterj.mqtt.client;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;

/**
 * @author master
 * @create 2019-09-16 16:31
 */
public class SubscribeClient {



    public static void main(String[] args) throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost("mqtt.p2hp.com", 1883);
//        mqtt.setHost("tcp://localhost:1883")
        mqtt.setCleanSession(false);
        mqtt.setClientId("1");

        BlockingConnection connection = mqtt.blockingConnection();
        connection.connect();
        Topic[] topics = {new Topic("foo", QoS.AT_LEAST_ONCE)};
        byte[] qoses = connection.subscribe(topics);
        System.out.println("qoses = " + new String(qoses));

        Message message = connection.receive();
        System.out.println("topic = " + message.getTopic());
        byte[] payload = message.getPayload();
        System.out.println("payload = " + new String(payload));
// process the message then:
        message.ack();
        Thread.sleep(1000000L);
    }




/**
    public static void main(String[] args) throws Exception {
        MQTT mqtt = new MQTT();
        mqtt.setHost("mqtt.p2hp.com", 1883);
        final CallbackConnection connection = mqtt.callbackConnection();





        connection.listener(new ExtendedListener() {

            @Override
            public void onPublish(UTF8Buffer topic, Buffer body, Callback<Callback<Void>> ack) {
                System.out.println("ack = " + ack);
            }

            @Override
            public void onDisconnected() {
                System.out.println("onDisconnected");
            }
            @Override
            public void onConnected() {
                System.out.println("onConnected");
            }

            @Override
            public void onPublish(UTF8Buffer topic, Buffer payload, Runnable ack) {
                // You can now process a received message from a topic.
                // Once process execute the ack runnable.
                ack.run();
                System.out.println("onPublish======");
            }

            @Override
            public void onFailure(Throwable value) {
                System.out.println("onFailure=======");
//                connection.close(null); // a connection failure occured.
            }
        });



        connection.connect(new Callback<Void>() {
            @Override
            public void onFailure(Throwable value) {
//                result.failure(value); // If we could not connect to the server.
                System.out.println("onFailure--");
            }

            // Once we connect..
            @Override
            public void onSuccess(Void v) {
                System.out.println("onSuccess--");
                // Subscribe to a topic
                Topic[] topics = {new Topic("foo", QoS.AT_LEAST_ONCE)};

                connection.subscribe(topics, new Callback<byte[]>() {
                    @Override
                    public void onSuccess(byte[] qoses) {
                        // The result of the subcribe request.
                        System.out.println("subscribe-------onSuccess");
                    }
                    @Override
                    public void onFailure(Throwable value) {
//                        connection.close(null); // subscribe failed.
                        System.out.println("subscribe-------onFailure");
                    }
                });

                // Send a message to a topic
                connection.publish("foo", "Hello".getBytes(), QoS.AT_LEAST_ONCE, false, new Callback<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // the pubish operation completed successfully.
                        System.out.println("publish-------onSuccess");
                    }
                    @Override
                    public void onFailure(Throwable value) {
//                        connection.close(null); // publish failed.
                        System.out.println("publish-------onFailure");
                    }
                });

                // To disconnect..
                connection.disconnect(new Callback<Void>() {
                    @Override
                    public void onSuccess(Void v) {
                        // called once the connection is disconnected.
                    }
                    @Override
                    public void onFailure(Throwable value) {
                        // Disconnects never fail.
                    }
                });
            }
        });

        Thread.sleep(100000000000L);
    }
    */
}


