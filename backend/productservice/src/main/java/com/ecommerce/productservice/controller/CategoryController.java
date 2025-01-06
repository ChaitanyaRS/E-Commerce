package com.ecommerce.productservice.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    // private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/{categoryName}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String categoryName){
        return categoryService.getProductByCategory(categoryName);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Category>> getCategoriesList(){
        return categoryService.getAllCategoriesList();

    }
}
