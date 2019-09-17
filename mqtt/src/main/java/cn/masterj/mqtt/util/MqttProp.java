package cn.masterj.mqtt.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author master
 * @create 2019-09-17 11:00
 * mqtt的属性配置类
 */

@Data
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MqttProp {

    /**
     * 消息服务器的URL
     */
    private String brokerUrl;

    /**
     * 客户端ID，用来标识一个客户，可以根据不同的策略来生成
     */
    private String clientId;

    /**
     * 订阅的主题名
     */
    private String topic;

    private String userName;

    private String passWord;

}
