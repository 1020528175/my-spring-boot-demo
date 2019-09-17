package cn.masterj.mqtt.application;

import cn.masterj.mqtt.service.SubscribeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author master
 * @create 2019-09-17 11:28
 */
@Slf4j
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {


    /**
     * spring 容器初始化后就启动mqtt的订阅
     * @param contextRefreshedEvent
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        SubscribeService subscribeService = contextRefreshedEvent.getApplicationContext().getBean(SubscribeService.class);
        subscribeService.start();
        log.info("----------------启动MQTT订阅成功----------------");
    }
}
