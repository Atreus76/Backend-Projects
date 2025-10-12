package com.example.simple_ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Product product;
    private int quantity;
}
