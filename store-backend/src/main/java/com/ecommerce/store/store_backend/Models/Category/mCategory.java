package com.ecommerce.store.store_backend.Models.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

public class mCategory {

    @Data
    @NoArgsConstructor
    public static class Category{
        private int categoryId;
        private String categoryName;
        private String description;
        private Integer parentCategoryId;

        public Category(int categoryId, String categoryName, String description, Integer parentCategoryId) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.description = description;
            this.parentCategoryId = parentCategoryId;
        }
    }
    @Data
    @NoArgsConstructor
    public static  class SubCategory{
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
