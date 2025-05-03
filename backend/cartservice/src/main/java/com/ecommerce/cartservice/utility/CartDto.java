package com.ecommerce.cartservice.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDto {
    private List<CartItemDto> cartItemDto;
    private double price;
}
