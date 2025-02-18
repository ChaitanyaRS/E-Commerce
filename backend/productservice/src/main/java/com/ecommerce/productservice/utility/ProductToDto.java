package com.ecommerce.productservice.utility;

import com.ecommerce.productservice.entity.Product;

public class ProductToDto {
    public static ProductDto productDtoConversion(Product product,int userId){
        return new ProductDto(product.getPid(),userId,product.getQuantity());
    }
}
