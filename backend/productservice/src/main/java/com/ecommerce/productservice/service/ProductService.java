package com.ecommerce.productservice.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repo.ProductRepo;
import com.ecommerce.productservice.utility.ProductDto;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productsList = productRepo.findAll();
        return ResponseEntity.ok().body(productsList);
    }

    public ResponseEntity<String> addProduct(ProductDto product){


        return ResponseEntity.ok().body("Added");
    }
}
