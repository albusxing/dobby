package com.albusxing.dobby.mq;

import com.albusxing.dobby.common.base.BaseMqModel;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * MQ 生产者服务
 */
@Component
@Slf4j
public class MqProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${mq.exchange}")
    private String exchange;

    @Value("${mq.queue}")
    private String queue;

    @Value("${mq.routingKey}")
    private String routingKey;


    public <T> void sendMqMessage(T data, String command, String desc) {
        BaseMqModel<T> model = new BaseMqModel();
        model.setData(data);
        model.setIndexNo(String.valueOf(System.currentTimeMillis()));
        model.setCommand(command);
        model.setDesc(desc);
        log.info("sendMqMessage -> exchange:{}, routingKey:{}, msg:{}", exchange, routingKey, JSONObject.toJSONString(model));
        rabbitTemplate.convertAndSend(exchange, routingKey, model);
    }
}
