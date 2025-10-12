package com.example.simple_ecommerce.repository;

import com.example.simple_ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
