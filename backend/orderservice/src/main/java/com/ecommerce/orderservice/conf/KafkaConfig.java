package com.ecommerce.orderservice.conf;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;

import static com.ecommerce.orderservice.utility.Constants.*;

@Configuration
public class KafkaConfig {
    public NewTopic ordersTopic(){
        return new NewTopic(ORDERS_TOPIC,DEFAULT_PARTITIONS, DEFAULT_REPLICATION);
    }
}
