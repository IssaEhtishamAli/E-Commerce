package com.ecommerce.store.store_backend.Models.Auth;

import lombok.Data;

@Data
public class mLogin {
    private String Email;
    private  String Password;

    public mLogin(String email, String password) {
        Email = email;
        Password = password;
    }
}
