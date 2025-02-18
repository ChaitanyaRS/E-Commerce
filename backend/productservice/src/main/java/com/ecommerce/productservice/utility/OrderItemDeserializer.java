package com.ecommerce.productservice.utility;

import java.io.IOException;
import java.util.List;

import org.apache.kafka.common.serialization.Deserializer;

//import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OrderItemDeserializer implements Deserializer<Object>{

	 private final ObjectMapper objectMapper = new ObjectMapper();
	 
	 @Override
	    public Object deserialize(String topic, byte[] data) {
	        try {
	            JsonNode jsonNode = objectMapper.readTree(data);

	            // Check if the JSON is an array or a single object
	            if (jsonNode.isArray()) {
	                return objectMapper.readValue(data, objectMapper.getTypeFactory().constructCollectionType(List.class, OrderItem.class));
	            } else {
	                return objectMapper.readValue(data, OrderItem.class);
	            }
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to deserialize message from topic " + topic, e);
	        }
	    }
	
}
