package com.ecommerce.cartservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ecommerce.cartservice.utility.Constants;
import com.ecommerce.cartservice.utility.ProductDto;

@Service
public class KafkaConsumerService {
	
	@Autowired
	private CartService cartService;
	
	@KafkaListener(topics = Constants.PRODUCT_TOPIC ,groupId = "ecommerce-product")
	public void consume(ProductDto dto) {
		cartService.updateCartAccToInventory(dto.getProdId());
		System.out.println(dto);
	}
}
