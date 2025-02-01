package com.ecommerce.productservice.service;

import java.util.List;
import java.util.Optional;

import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.ProductDto;
import com.ecommerce.productservice.entity.Rating;
import com.ecommerce.productservice.exception.ProductNotFound;
import com.ecommerce.productservice.repo.CategoryRepo;
import com.ecommerce.productservice.repo.ProductRepo;
import com.ecommerce.productservice.repo.RatingRepo;
import com.ecommerce.productservice.utility.ProductForm;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private RatingRepo ratingRepo;
    @Autowired
    private KafkaService publisher;

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productsList = productRepo.findAll();
        if (productsList.isEmpty())
            throw new ProductNotFound("No products present in inventory !!");
        else
            return ResponseEntity.ok().body(productsList);
    }

    public ResponseEntity<String> addProduct(ProductForm productDto) {
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
        if (product.isEmpty())
            throw new ProductNotFound("No product with mentioned id is present !!");
        else
            return ResponseEntity.ok().body(product.get());
    }

    public ResponseEntity<String> removeProdFromInventory(int id,int userId) {
        Optional<Product> product = productRepo.findByPId(id);

        product.ifPresentOrElse(prod ->{
             prod.setQuantity(prod.getQuantity()-1);
             productRepo.save(prod);
            }, () -> {
            publisher.sendEvent(new ProductDto(id,userId));  
            throw new ProductNotFound("Product not found.!!");
            // publisher.sendEvent(new ProductDto(id, userId));
        });

        log.info("Product " + id + " removed from inventory ");

        return ResponseEntity.ok().body("Product removed successfully !!");

    }


    public ResponseEntity<Integer> checkProdAvailability(int id) {
        Optional<Product> prod = productRepo.findById(id);
        return prod.map((p) -> ResponseEntity.ok(p.getPid()))
                .orElseGet(() -> new ResponseEntity<>(-1, HttpStatus.NOT_FOUND));
//        return ResponseEntity.ok(product.getPid());
    }


}
