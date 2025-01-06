package com.ecommerce.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.service.RatingService;

@RestController
@RequestMapping("/rating")
public class RatingController {
     @Autowired
    private RatingService ratingService;
    
    @GetMapping("/{rid}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable int rid){
        return ratingService.getProductByRatings(rid);
    }

}
