package com.ecommerce.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCTSERVICE")
public interface ProductFeignService {

    @GetMapping("/product/availability/{id}")
    public ResponseEntity<Integer> checkProdAvailability(@PathVariable("id") int id);
}
