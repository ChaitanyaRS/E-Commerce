package com.ecommerce.productservice.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ecommerce.productservice.utility.ProductToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.service.ProductService;
import com.ecommerce.productservice.utility.ProductDto;
import com.ecommerce.productservice.utility.ProductForm;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all-products")
    public CompletableFuture<ResponseEntity<List<Product>>> getAllProduct() {
        return productService.getAllProducts();
    }

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody ProductForm product) {
        return productService.addProduct(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductForm> getProductById(@PathVariable int id) {
        Product prod = productService.getProductById(id);
        ProductForm prodDto = ProductToDto.productFormDto(prod);
        return ResponseEntity.ok(prodDto);
    }

    @GetMapping("/qty-prod/{pId}")
    public CompletableFuture<ResponseEntity<Integer>> getQuantityOfSpecProd(@PathVariable int pId){
        return productService.getQtyOfSpecProd(pId);
    }

    @GetMapping("/availability/{id}")
    public ResponseEntity<Integer> checkProdAvailability(@PathVariable("id") int id) {
        return productService.checkProdAvailability(id);
    }

    @PostMapping("/populate")
    public ResponseEntity<String> populateProducts(){
        return productService.populateProducts();
    }

    @GetMapping("/get-price/{id}")
    public CompletableFuture<ResponseEntity<Double>> getProdPrice(@PathVariable("id") int id){
        return productService.getProdPrice(id);
    }

}
