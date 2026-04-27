package org.example.cartproject.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequestDTO {

    @NotBlank(message ="Product Name cannot be empty")
    private String productName;

    @NotNull(message = "Quantity cannot be null")
    private int quantity;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price should be greater then 0.00")
    private double priceOfOneItem;

}
