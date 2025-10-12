package com.example.simple_ecommerce.repository;

import com.example.simple_ecommerce.model.Cart;
import com.example.simple_ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
