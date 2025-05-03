package com.ecommerce.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CARTSERVICE")
public interface CartFeignService {

    @DeleteMapping("/cart/empty-cart/{userId}")
    public ResponseEntity<String> emptyCart(@PathVariable("userId") int userId);


}
