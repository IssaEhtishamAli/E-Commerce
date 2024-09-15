package com.ecommerce.store.store_backend.Mappers;

import com.ecommerce.store.store_backend.Models.Users.mUsers;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailsRowMapper  implements RowMapper<mUsers.GetUserForAuthentication> {
    @Override
    public mUsers.GetUserForAuthentication mapRow(ResultSet rs,int rowNum) throws SQLException {
        mUsers.GetUserForAuthentication user= new mUsers.GetUserForAuthentication();
        user.setUserName(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}
