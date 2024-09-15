package com.ecommerce.store.store_backend.Models.Auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class mJwtResponse {
    private String JwtToken;
    private int UserId;
    private String UserName;
//    private LocalDateTime TokenExpiry;
    private String ProfilePicture;
//    private LocalDateTime LastLogin;

    public mJwtResponse(String jwtToken, int userId, String userName, String profilePicture) {
        JwtToken = jwtToken;
        UserId = userId;
        UserName = userName;
//        TokenExpiry = tokenExpiry;
        ProfilePicture = profilePicture;
//        LastLogin = lastLogin;
    }
}
