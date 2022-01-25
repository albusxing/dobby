package com.albusxing.dobby.mq;


import com.albusxing.dobby.common.base.BaseMqModel;
import com.albusxing.dobby.common.constant.MqConstant;
import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * MQ 消费者服务
 * @author liguoqing
 */
@Slf4j
@Component
public class MqConsumer {

    @RabbitListener(bindings = @QueueBinding(
                    value = @Queue(value = MqConstant.QUEUE_MODULE_FUNC, ignoreDeclarationExceptions = "true"),
                    exchange = @Exchange(value = MqConstant.EXCHANGE, ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
                    key = MqConstant.ROUTING_KEY_MODULE_FUNC))
    @RabbitHandler
    public void receiveMqMessage(Channel channel, Message message, BaseMqModel<Object> model) throws Exception {
        log.info("receiveMqMessage -> request : {}", JSON.toJSONString(model));
        String command = model.getCommand();
        if (StringUtils.isBlank(command)) {
            log.info("mq command is null, do not execute");
            return;
        }
        try {
            //执行具体的业务

            //消息确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("execute message error", e);
            //失败，消息确认
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
