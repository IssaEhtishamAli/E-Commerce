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
        jwtData.setUserId(rs.getInt("user_id"));
        jwtData.setUserName(rs.getString("username"));
        jwtData.setEmail(rs.getString("email"));
        jwtData.setPassword(rs.getString("password"));
        jwtData.setProfilePicture(rs.getString("profile_picture_url"));
        Timestamp timestamp = rs.getTimestamp("token_expiry");
        Timestamp timestamps = rs.getTimestamp("last_login");
        if (timestamp != null && timestamps !=null ) {
            jwtData.setLastLogin(timestamps.toLocalDateTime());
            jwtData.setTokenExpiry(timestamp.toLocalDateTime());
        } else {
            jwtData.setLastLogin(null);
            jwtData.setTokenExpiry(null); // or handle default value
        }
        return jwtData;

    }
}
