package org.example.cartproject.controller;

import jakarta.validation.Valid;
import org.example.cartproject.dto.CartItemRequestDTO;
import org.example.cartproject.dto.CartItemResponseDTO;
import org.example.cartproject.model.CartItem;
import org.example.cartproject.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {


    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemResponseDTO>> getCartItems(){
        List<CartItemResponseDTO> responseDTOList = new ArrayList<>();
        List<CartItem> items = cartService.getAllItems();
        items.forEach(cartItem -> {
            CartItemResponseDTO responseDTO = new CartItemResponseDTO(cartItem.getId(), cartItem.getProductName(),
                    cartItem.getQuantity(), cartItem.getPrice()*cartItem.getQuantity());
            responseDTOList.add(responseDTO);
        });
        return ResponseEntity.ok(responseDTOList);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addCartItem(@Valid @RequestBody CartItemRequestDTO cartItemRequest){
        CartItem item = new CartItem();
        item.setPrice(cartItemRequest.getPriceOfOneItem());
        item.setQuantity(cartItemRequest.getQuantity());
        item.setProductName(cartItemRequest.getProductName());
        CartItem savedItem = cartService.save(item);
        CartItemResponseDTO responseDTO = new CartItemResponseDTO(savedItem.getId(),savedItem.getProductName(), savedItem.getQuantity(), savedItem.getPrice()* savedItem.getQuantity());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }



}
