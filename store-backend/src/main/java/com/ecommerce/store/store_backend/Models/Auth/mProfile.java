package com.ecommerce.store.store_backend.Models.Auth;

import lombok.Data;

public class mProfile {
    @Data
    public static  class UploadPictures{
        private String image;
        private String fileName;

        public UploadPictures(String image, String fileName) {
            this.image = image;
            this.fileName = fileName;
        }
    }
}
