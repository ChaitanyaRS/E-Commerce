package com.ecommerce.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.Rating;
import com.ecommerce.productservice.exception.ProductNotFound;
import com.ecommerce.productservice.repo.CategoryRepo;
import com.ecommerce.productservice.repo.ProductRepo;
import com.ecommerce.productservice.repo.RatingRepo;

@Service
public class RatingService {
@Autowired
    private RatingRepo ratingRepo;

    @Autowired
    private ProductRepo productRepo;
    // private Stri

    public ResponseEntity<List<Product>> getProductByRatings(int id) {

        Rating rating =ratingRepo.findById(id).get();
        List<Product> productsList = productRepo.findByRId(rating.getRId());
        if(productsList.isEmpty())
            throw new ProductNotFound("No products present in inventory !!");
        else
            return ResponseEntity.ok().body(productsList);
    }
}
