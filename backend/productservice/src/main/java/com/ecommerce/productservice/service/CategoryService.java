package com.ecommerce.productservice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.exception.ProductNotFound;
import com.ecommerce.productservice.repo.CategoryRepo;
import com.ecommerce.productservice.repo.ProductRepo;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;
    // private Stri

    public ResponseEntity<List<Product>> getProductByCategory(String category) {

        Category cat =categoryRepo.findByCategoryName(category);
        List<Product> productsList = productRepo.findByCId(cat.getCId());
        if(productsList.isEmpty())
            throw new ProductNotFound("No products present in inventory !!");
        else
            return ResponseEntity.ok().body(productsList);
    }

    public ResponseEntity<List<Category>> getAllCategoriesList() {
       return ResponseEntity.ok().body(categoryRepo.findAll());
    }

}
