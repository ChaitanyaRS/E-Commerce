package com.ecommerce.productservice.utility;

import com.ecommerce.productservice.entity.Category;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.Rating;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ProductToDto {
    public static ProductDto productDtoConversion(Product product,int userId){
        return new ProductDto(product.getPid(),userId,product.getQuantity());
    }

    public static ProductForm productFormDto(Product product){
        return new ProductForm(product.getPid(), product.getProductName(),product.getDescription(),product.getPrice(), product.getQuantity(),
                product.getCategory().getCategoryName(),product.getImageLink(),product.getRating().getRatings());
    }

//    private String productName;
//    private String description;
//    private double price;
//    private int quantity;
//    private String category;
//    private String imageLink;
//    private int ratings;

//    private int pid;
//    private String productName;
//    private String description;
//    private double price;
//    private int quantity;
//    private String imageLink;
//    @JoinColumn(name = "c_id")
//    @JsonManagedReference
//    @ManyToOne
//    private Category category;
//    @ManyToOne
//    @JsonManagedReference
//    @JoinColumn(name = "r_id")
//    private Rating rating;
}
