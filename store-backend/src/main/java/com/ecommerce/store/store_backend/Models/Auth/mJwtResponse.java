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
    private String ProfilePicture;
    private LocalDateTime LastLogin;
    private LocalDateTime TokenExpiry;

    public mJwtResponse(String jwtToken, int userId, String userName, String profilePicture,LocalDateTime lastLogin,LocalDateTime tokenExpiry) {
        JwtToken = jwtToken;
        UserId = userId;
        UserName = userName;
        ProfilePicture = profilePicture;
        LastLogin= lastLogin;
        TokenExpiry = tokenExpiry;
    }
}
