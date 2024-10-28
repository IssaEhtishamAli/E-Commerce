package com.ecommerce.store.store_backend.Models.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class mCategory {

    @Data
    @NoArgsConstructor
    public static class Category {
        private int categoryId;
        private String categoryName;
        private String description;
        private List<SubCategory> subcategories; // Add subcategories list

        public Category(int categoryId, String categoryName, String description, List<SubCategory> subcategories) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.description = description;
            this.subcategories = subcategories;
        }
    }

    @Data
    @NoArgsConstructor
    public static class SubCategory {
        private int subcategoryId;
        private String subcategoryName;
        private String description;
        private int categoryId;

        public SubCategory(int subcategoryId, String subcategoryName, String description, int categoryId) {
            this.subcategoryId = subcategoryId;
            this.subcategoryName = subcategoryName;
            this.description = description;
            this.categoryId = categoryId;
        }
    }
}