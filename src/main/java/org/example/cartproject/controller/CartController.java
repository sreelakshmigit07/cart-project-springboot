package org.example.cartproject.controller;

import jakarta.validation.Valid;
import org.example.cartproject.dto.CartItemRequestDTO;
import org.example.cartproject.dto.CartItemResponseDTO;
import org.example.cartproject.error.NotFoundError;
import org.example.cartproject.model.CartItem;
import org.example.cartproject.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id){

        if(cartService.getById(id).isPresent()) {
          cartService.delete(id);
          return ResponseEntity.ok("Item successfully Removed from cart!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCartItem(@PathVariable Long id ,@Valid @RequestBody CartItemRequestDTO requestDTO){
        Optional<CartItem> itemOptional = cartService.getById(id);
        if (itemOptional.isPresent()) {
            CartItem existing = itemOptional.get();
            existing.setProductName(requestDTO.getProductName());
            existing.setQuantity(requestDTO.getQuantity());
            existing.setPrice(requestDTO.getPriceOfOneItem());
            CartItem savedItem = cartService.save(existing);
            return  ResponseEntity.ok(new CartItemResponseDTO(savedItem.getId(), savedItem.getProductName(), savedItem.getQuantity(), savedItem.getPrice()));

        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new NotFoundError("Item not Found!"));
        }
    }



}
