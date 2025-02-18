package com.ecommerce.productservice.service;

import java.util.List;
import java.util.Optional;

import feign.Response;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.Rating;
import com.ecommerce.productservice.exception.ProductNotFound;
import com.ecommerce.productservice.repo.CategoryRepo;
import com.ecommerce.productservice.repo.ProductRepo;
import com.ecommerce.productservice.repo.RatingRepo;
import com.ecommerce.productservice.utility.OrderItem;
import com.ecommerce.productservice.utility.ProductDto;
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
    private KafkaPublisherService publisher;

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

    
    @Transactional
	public ResponseEntity<String> removeProdFromInventory(List<OrderItem> items) {
		
		//Check for product available if yes then change the quantity.
    	for (OrderItem item : items) {
			Optional<Product> product = productRepo.findById(item.getProdId());
			Product prod = product.orElseThrow(()->new ProductNotFound("Few products are out of stock"));
			int orderedQuantity = item.getQuantity();
			int productQuantity = prod.getQuantity();
			//If available quantity is greater than or equal to available quantity then do substraction.
			if(productQuantity >= orderedQuantity) {
				//
				prod.setQuantity(productQuantity - orderedQuantity);
				
				//Sending event to carts to update their carts accordingly.
				publisher.notifyCartsAboutOrder(new ProductDto(prod.getPid(),item.getUserId(),item.getQuantity()));
			}else {
				//if available Quantity is less than required then throw error and ask to order again.
				throw new ProductNotFound("All products with required quantity are not present. Please try again.");
			}
		}   	
    	
		return ResponseEntity.ok("Removed !!");
	
	}
    
    //
//    public ResponseEntity<String> removeProdFromInventory(OrderItem items) {
//        Optional<Product> product = productRepo.findByPId(id);
//
//        product.ifPresentOrElse(prod ->{
//             prod.setQuantity(prod.getQuantity()-1);
//             productRepo.save(prod);
//            }, () -> {
//            publisher.sendEvent(new ProductDto(id,userId));  
//            throw new ProductNotFound("Product not found.!!");
//            // publisher.sendEvent(new ProductDto(id, userId));
//        });
//
//        log.info("Product " + id + " removed from inventory ");
//
//        return ResponseEntity.ok().body("Product removed successfully !!");
//
//    }


    public ResponseEntity<Integer> checkProdAvailability(int id) {
        Optional<Product> prod = productRepo.findById(id);
        return prod.map((p) -> ResponseEntity.ok(p.getPid()))
                .orElseGet(() -> new ResponseEntity<>(-1, HttpStatus.NOT_FOUND));
//        return ResponseEntity.ok(product.getPid());
    }


    public ResponseEntity<Integer> getQtyOfSpecProd(int pId) {
        return ResponseEntity.ok(productRepo.findByPId(pId).get().getQuantity());
    }
}
