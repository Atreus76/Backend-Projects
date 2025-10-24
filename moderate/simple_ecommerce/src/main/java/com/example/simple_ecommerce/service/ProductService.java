package com.example.simple_ecommerce.service;

import com.example.simple_ecommerce.dto.ProductDTO;
import com.example.simple_ecommerce.model.Cart;
import com.example.simple_ecommerce.model.CartItem;
import com.example.simple_ecommerce.model.Category;
import com.example.simple_ecommerce.model.Product;
import com.example.simple_ecommerce.repository.CartItemRepository;
import com.example.simple_ecommerce.repository.CategoryRepository;
import com.example.simple_ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Product createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setCategory(category);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existing.setName(productDTO.getName());
        existing.setPrice(productDTO.getPrice());
        existing.setStock(productDTO.getStock());
        existing.setCategory(category);
        return productRepository.save(existing);
    }

    public List<Product> getAllProducts(Optional<Long> categoryId) {
        if (categoryId.isPresent()) {
            return productRepository.findByCategoryId(categoryId.get());
        }
        return productRepository.findAll();
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        List<CartItem> cartItems = cartItemRepository.findByProduct(product);
        cartItemRepository.deleteAll(cartItems);
        productRepository.delete(product);
    }
}
