package com.example.simple_ecommerce.dto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class ProductDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Positive(message = "Price must be positive")
    private double price;

    @PositiveOrZero(message = "Stock cannot be negative")
    private int stock;

    @Positive(message = "Category ID must be positive")
    private Long categoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
