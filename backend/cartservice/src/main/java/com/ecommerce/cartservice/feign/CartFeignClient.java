package com.ecommerce.cartservice.feign;

import com.ecommerce.cartservice.utility.CartItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCTSERVICE")
public interface CartFeignClient {

    @GetMapping("/product/qty-prod/{pId}")
    public ResponseEntity<Integer> getQuantityOfSpecProd(@PathVariable("pId") int pId);

    @GetMapping("/product/{pId}")
    public ResponseEntity<CartItemDto> getProductById(@PathVariable("pId") int id);
}

