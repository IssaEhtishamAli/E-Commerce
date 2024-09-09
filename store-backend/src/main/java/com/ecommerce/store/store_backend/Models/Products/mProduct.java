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
        private int subcategory_id;

        public Product(int productId, String name, String description, double price, int stockQuantity, int subcategory_id) {
            this.productId = productId;
            this.name = name;
            this.description = description;
            this.price = price;
            this.stockQuantity = stockQuantity;
            this.subcategory_id = subcategory_id;
        }
    }
}
