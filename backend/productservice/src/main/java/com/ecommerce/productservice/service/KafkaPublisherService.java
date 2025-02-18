package com.ecommerce.productservice.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.constants.Constants;
import com.ecommerce.productservice.utility.ProductDto;

@Service
public class KafkaPublisherService {

    @Autowired
    private KafkaTemplate<String,Object> template;

    public void notifyCartsAboutOrder(ProductDto prod){
        CompletableFuture<SendResult<String, Object>> future = template.send(Constants.PRODUCT_TOPIC,prod);
        future.whenComplete((response,ex)->{
            if(ex == null){
                System.out.println("Object sent "+prod+" offsetno: "+response.getRecordMetadata().offset());
            }else{
                System.out.println(ex.getMessage());
            }
        });
    }

}
