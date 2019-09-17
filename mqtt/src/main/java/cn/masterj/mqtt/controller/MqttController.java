package cn.masterj.mqtt.controller;

import cn.masterj.mqtt.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author master
 * @create 2019-09-17 13:57
 */
@RestController
@RequestMapping("/mqtt")
public class MqttController {

    @Autowired
    private PublishService publishService;

    @GetMapping("/send")
    public String send(){
        try {
            publishService.send();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "发送成功";
    }
}
