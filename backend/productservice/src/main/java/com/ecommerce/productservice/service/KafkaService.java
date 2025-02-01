package com.ecommerce.productservice.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ecommerce.productservice.utility.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import static com.ecommerce.productservice.constants.Constants.*;

import com.ecommerce.productservice.constants.Constants;
import com.ecommerce.productservice.entity.ProductDto;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String,Object> template;

    public void sendEvent(ProductDto prod){
         CompletableFuture<SendResult<String, Object>> future = template.send(Constants.PRODUCT_TOPIC,prod);
        future.whenComplete((response,ex)->{
            if(ex == null){
                System.out.println("Object sent "+prod+" offsetno: "+response.getRecordMetadata().offset());
            }else{
                System.out.println(ex.getMessage());
            }
        });
    }

    @KafkaListener(topics = ORDER_TOPIC,groupId = "ecommerce-product")
    public void orderPlacedListener(OrderItem [] items){
        for (int i = 0; i < items.length; i++) {
            System.out.println(items);
        }
    }
}
