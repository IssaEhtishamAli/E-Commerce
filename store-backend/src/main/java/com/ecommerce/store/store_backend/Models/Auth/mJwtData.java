package com.ecommerce.store.store_backend.Models.Auth;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import java.time.LocalDateTime;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Scope(scopeName = "prototype")
@Data
public class mJwtData {
    private String userName;
    private String Password;
    private String Email;
    private int UserId;
    private String ProfilePicture;
    private LocalDateTime LastLogin;
    private LocalDateTime TokenExpiry;

}
