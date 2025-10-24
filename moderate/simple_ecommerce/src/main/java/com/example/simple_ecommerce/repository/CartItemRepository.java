package com.example.simple_ecommerce.repository;

import com.example.simple_ecommerce.model.CartItem;
import com.example.simple_ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByProduct(Product product);
}
