package com.ecommerce.store.store_backend.Models.Category;

import lombok.Data;
import lombok.NoArgsConstructor;

public class mCategory {

    @Data
    @NoArgsConstructor
    public static class Category{
        private int id;
        private String name;
        private String description;

        public Category(int id, String name, String description) {
            this.id = id;
            this.name = name;
            this.description = description;
        }
    }

}
