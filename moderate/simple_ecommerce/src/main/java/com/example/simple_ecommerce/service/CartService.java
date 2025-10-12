package com.example.simple_ecommerce.service;

import com.example.simple_ecommerce.model.Cart;
import com.example.simple_ecommerce.model.CartItem;
import com.example.simple_ecommerce.model.Product;
import com.example.simple_ecommerce.model.User;
import com.example.simple_ecommerce.repository.CartItemRepository;
import com.example.simple_ecommerce.repository.CartRepository;
import com.example.simple_ecommerce.repository.ProductRepository;
import com.example.simple_ecommerce.repository.UserRepository;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public Cart addToCart(String username, Long productId, int quantity) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cart.getItems().add(cartItem);
        cartItemRepository.save(cartItem);

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        return cartRepository.save(cart);
    }

    public Cart getCart(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public String checkout(String username, String stripeToken) {
        Stripe.apiKey = stripeApiKey;
        Cart cart = getCart(username);
        double total = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", (int) (total * 100)); // Amount in cents
            params.put("currency", "usd");
            params.put("source", stripeToken);
            params.put("description", "E-commerce checkout for " + username);
            Charge charge = Charge.create(params);
            cart.getItems().clear();
            cartRepository.save(cart);
            return charge.getId();
        } catch (Exception e) {
            throw new RuntimeException("Payment failed: " + e.getMessage());
        }
    }
}
