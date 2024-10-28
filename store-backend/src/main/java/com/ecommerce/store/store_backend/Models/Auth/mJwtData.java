package com.ecommerce.store.store_backend.Models.Auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Scope(scopeName = "prototype")
@Data
public class mJwtData {
    private String userName;
    private String Password;
    private String Email;
    private int UserId;
    private LocalDateTime TokenExpiry;
    private String ProfilePicture;
    private LocalDateTime LastLogin;
}
