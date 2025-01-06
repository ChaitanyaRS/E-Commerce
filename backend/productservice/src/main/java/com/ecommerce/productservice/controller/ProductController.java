package com.ecommerce.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.service.ProductService;
import com.ecommerce.productservice.utility.ProductDto;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all-products")
    public ResponseEntity<List<Product>> getAllProduct(){
        return productService.getAllProducts();
    }

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody ProductDto product){
        return productService.addProduct(product);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        return productService.getProductById(id);
    }
}
