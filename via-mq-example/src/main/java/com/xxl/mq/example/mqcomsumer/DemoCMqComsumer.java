package com.xxl.mq.example.mqcomsumer;

import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.annotation.MqConsumer;
import com.xxl.mq.client.rpc.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.xxl.mq.client.consumer.annotation.MqConsumerType.TOPIC;

/**
 * 消息模型 3/3 : TOPIC = 广播消息 : 发布/订阅模式, 一条消息将会广播发送给所有在线的Consumer
 * Created by xuxueli on 16/8/28.
 */
@MqConsumer(value = "mqconsumer-03", type = TOPIC)
@Service
public class DemoCMqComsumer implements IMqConsumer {
    private Logger logger = LoggerFactory.getLogger(DemoCMqComsumer.class);

    @Override
    public void consume(Map<String, String> data) throws Exception {
        logger.info("TOPIC(广播消息): {}消费一条消息:{}", "mqconsumer-02",  JacksonUtil.writeValueAsString(data));
    }

}
