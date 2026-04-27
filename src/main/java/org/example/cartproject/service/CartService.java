package org.example.cartproject.service;

import org.example.cartproject.model.CartItem;
import org.example.cartproject.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CartService {


    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }


    public List<CartItem> getAllItems(){
        return cartRepository.findAll();
    }

    public CartItem save(CartItem cartItem){
        return cartRepository.save(cartItem);
    }

    public Optional<CartItem> getById(Long Id){
        return cartRepository.findById(Id);
    }

    public void delete(Long id){
        cartRepository.deleteById(id);
    }
}
