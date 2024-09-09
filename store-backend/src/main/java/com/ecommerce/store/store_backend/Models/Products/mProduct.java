package com.ecommerce.store.store_backend.Models.Products;

import lombok.Data;
import lombok.NoArgsConstructor;

public class mProduct {
    @Data
    @NoArgsConstructor
    public static class Product{
        private int productId;
        private String name;
        private String description;
        private double price;
        private int stockQuantity;
        private int categoryId;

        public Product(int productId, String name, String description, double price, int stockQuantity, int categoryId) {
            this.productId = productId;
            this.name = name;
            this.description = description;
            this.price = price;
            this.stockQuantity = stockQuantity;
            this.categoryId = categoryId;
        }
    }
}
