package com.ecommerce.productservice.utility;

import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.entity.ProductDto;

public class ProductToDto {
    public static ProductDto productDtoConversion(Product product,int userId){
        return new ProductDto(product.getPid(),userId);
    }
}
