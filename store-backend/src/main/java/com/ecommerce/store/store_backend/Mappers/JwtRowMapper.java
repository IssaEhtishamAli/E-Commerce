package com.ecommerce.store.store_backend.Mappers;



import com.ecommerce.store.store_backend.Models.Auth.mJwtData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JwtRowMapper implements RowMapper<mJwtData> {
    @Override
    public mJwtData mapRow(ResultSet rs, int rowNum) throws SQLException {

        mJwtData jwtData= new mJwtData();
        // Map other fields like username, email, etc.
        jwtData.setUserName(rs.getString("username"));
        jwtData.setEmail(rs.getString("email"));
        // ... other fields

        // Map the tokenExpiry (TIMESTAMP without timezone)
//        Timestamp timestamp = rs.getTimestamp("token_expiry");
//        if (timestamp != null) {
//            jwtData.setLastLogin(timestamp.toLocalDateTime()); // Setting the LocalDateTime
//        } else {
//            jwtData.setLastLogin(null); // Handle null case appropriately
//        }
        return jwtData;

    }
}
