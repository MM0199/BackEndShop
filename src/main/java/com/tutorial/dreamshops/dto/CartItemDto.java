package com.tutorial.dreamshops.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long itemId;
    private int quantity;
    private BigDecimal totalPrice;
    private ProductDto product;
}
