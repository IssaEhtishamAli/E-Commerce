package com.ecommerce.store.store_backend.Models.Auth;

import lombok.Data;
import lombok.NoArgsConstructor;

public class mEmailVerification {
    @Data
    @NoArgsConstructor
    public static class Email{
        private String Email;

        public Email(String email) {
            Email = email;
        }
    }
}
