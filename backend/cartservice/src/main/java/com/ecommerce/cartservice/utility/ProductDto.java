package com.ecommerce.cartservice.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
public class ProductDto {
    private int prodId;
    private int userId;
    private int quantity;
}
