package com.ecommerce.store.store_backend.Models.Products;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
        private String image_url;
        public Product(int productId, String name, String description, double price, int stockQuantity, int subcategory_id, String image_url) {
            this.productId = productId;
            this.name = name;
            this.description = description;
            this.price = price;
            this.stockQuantity = stockQuantity;
            this.subcategory_id = subcategory_id;
            this.image_url = image_url;
        }
    }
    @Data
    @NoArgsConstructor
    public static class ProductInventory{
        private int inventoryId;
        private int productId;
        private int userId;           // Reference to the user who made the inventory change
        private int changeAmount;     // The change in stock (positive or negative)
        private String changeReason;
        private Timestamp changeDate;
        private int remainingStock;

        public ProductInventory(int inventoryId, int productId, int userId, int changeAmount, String changeReason, Timestamp changeDate, int remainingStock) {
            this.inventoryId = inventoryId;
            this.productId = productId;
            this.userId = userId;
            this.changeAmount = changeAmount;
            this.changeReason = changeReason;
            this.changeDate = changeDate;
            this.remainingStock = remainingStock;
        }
    }
}
