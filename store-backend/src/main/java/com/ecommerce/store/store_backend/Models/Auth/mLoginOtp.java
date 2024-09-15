package com.ecommerce.store.store_backend.Models.Auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class mLoginOtp {
    private  String Email;
    private String Password;
    private String Otp;

    public mLoginOtp(String email, String password, String otp) {
        Email = email;
        Password = password;
        Otp = otp;
    }
}
