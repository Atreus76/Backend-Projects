package com.example.simple_ecommerce.controller;

import com.example.simple_ecommerce.model.Cart;
import com.example.simple_ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(Authentication authentication, @RequestBody CartRequest request) {
        return ResponseEntity.ok(cartService.addToCart(authentication.getName(), request.getProductId(), request.getQuantity()));
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(Authentication authentication) {
        return ResponseEntity.ok(cartService.getCart(authentication.getName()));
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(Authentication authentication, @RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(cartService.checkout(authentication.getName(), request.getStripeToken()));
    }

    static class CartRequest {
        private Long productId;
        private int quantity;

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    static class CheckoutRequest {
        private String stripeToken;

        public String getStripeToken() { return stripeToken; }
        public void setStripeToken(String stripeToken) { this.stripeToken = stripeToken; }
    }
}
