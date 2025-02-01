package com.ecommerce.productservice.config;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.ecommerce.productservice.constants.Constants;
import com.ecommerce.productservice.utility.OrderItem;
import com.fasterxml.jackson.core.type.TypeReference;

@Configuration
@EnableKafka
public class KafkaConfig {

    // Create a new Kafka topic with the specified name, partition count, and replication factor
    @Bean
    public NewTopic newTopic() {
        return new NewTopic(Constants.PRODUCT_TOPIC, Constants.DEFAULT_PARTITION, Constants.DEFAULT_REPLICATION);
    }

    // Consumer configuration settings (properties)
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> consumerProps = new HashMap<>();
//        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Replace with your Kafka server URL
//        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "ecommerce-product");
//        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//        return consumerProps;
//    }
//
//    // Create a ConsumerFactory with a JSON deserializer for List<OrderItem>
//    @Bean
//    public ConsumerFactory<String, List<OrderItem>> consumerFactory() {
//    	JsonDeserializer<OrderItem []> deserializer = new JsonDeserializer<>(new TypeReference<OrderItem []>() {});
//        deserializer.addTrustedPackages("com.ecommerce.productservice.utility");
//        deserializer.setUseTypeMapperForKey(true);
//        
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs(), new StringDeserializer(), deserializer);
//    }

}
