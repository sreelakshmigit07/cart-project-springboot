package org.example.cartproject.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemResponseDTO {

    private Long id;
    private String productName;
    private int quantity;
    private double totalPrice;

}
