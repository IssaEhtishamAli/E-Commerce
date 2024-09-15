package com.ecommerce.store.store_backend.Models.Auth;

import lombok.Data;

@Data
public class mChangePassword {
    private  String Email;
    private String Password;
    public mChangePassword(String email, String password) {
        Email = email;
        Password = password;
    }
}
