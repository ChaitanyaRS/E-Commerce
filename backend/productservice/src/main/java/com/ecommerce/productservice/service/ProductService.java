package com.ecommerce.productservice.service;

import java.util.List;
import java.util.Optional;

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
import com.ecommerce.productservice.utility.ProductDto;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private RatingRepo ratingRepo;

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productsList = productRepo.findAll();
        if(productsList.isEmpty())
            throw new ProductNotFound("No products present in inventory !!");
        else
            return ResponseEntity.ok().body(productsList);
    }

    public ResponseEntity<String> addProduct(ProductDto productDto) {
        Category category = categoryRepo.findByCategoryName(productDto.getCategory());
        Rating rating = ratingRepo.findById(productDto.getRatings()).get();
        Product product = new Product(productDto.getProductName(), productDto.getDescription(), productDto.getPrice(),
                productDto.getQuantity(), productDto.getImageLink(), category, rating);

        product.getCategory().addProdToList(product);
        product.getRating().addProdToList(product);
        productRepo.save(product);
        return ResponseEntity.ok().body("Added");
    }

    public ResponseEntity<Product> getProductById(int id) {
        Optional<Product> product = productRepo.findById(id);
        if(product.isEmpty())
            throw new ProductNotFound("No product with mentioned id is present !!");
        else
            return ResponseEntity.ok().body(product.get());
    }

}
