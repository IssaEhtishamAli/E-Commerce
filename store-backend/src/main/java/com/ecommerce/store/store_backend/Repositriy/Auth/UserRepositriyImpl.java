package com.ecommerce.store.store_backend.Repositriy.Auth;

import com.ecommerce.store.store_backend.Mappers.JwtRowMapper;
import com.ecommerce.store.store_backend.Mappers.UserDetailsRowMapper;
import com.ecommerce.store.store_backend.Models.Auth.mJwtData;
import com.ecommerce.store.store_backend.Models.Auth.mLogin;
import com.ecommerce.store.store_backend.Models.Auth.mSignUp;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Users.mUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Repository
public class UserRepositriyImpl implements IUserRepositriy{
    @Autowired
    private JdbcTemplate jdbctemplate;
    private  final String INSERT_USER ="INSERT INTO users (username, email, password, first_name, last_name, phone_number, profile_picture_url, address, is_active, dob, gender) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private  final String SELECT_BY_EMAIL= "SELECT * FROM users WHERE email = ?";
    @Override
    public mGeneric.mApiResponse saveUser(mSignUp.SignUp user) {
        try {
            BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            // Convert String to LocalDate
            int rowsAffected =  jdbctemplate.update(
                    INSERT_USER,
                    user.getUserName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getPhoneNumber(),
                    user.getProfilePicture(),
                    user.getAddress(),
                    user.isIsActive(),
                    user.getDob(),
                    user.getGender()
            );
            if(rowsAffected > 0){
                return  new mGeneric.mApiResponse<>(1,"User Created Successfully",HttpStatus.OK);
            }
            else{
                return new mGeneric.mApiResponse(0,"Failed to save");
            }
        }catch (Exception ex){
            return  new mGeneric.mApiResponse(0,"", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public mGeneric.mApiResponse<mUsers.users> findByEmail(String email) {
        try {
                mUsers.users rowsAffected = jdbctemplate.queryForObject(SELECT_BY_EMAIL, new Object[]{email}, new BeanPropertyRowMapper<>(mUsers.users.class));
                if (rowsAffected==null){
                    return new mGeneric.mApiResponse(0,"User not found!",HttpStatus.NOT_FOUND);
                }else{
                    return  new mGeneric.mApiResponse(1,"",HttpStatus.OK);
                }
        }catch (Exception ex){
            return new mGeneric.mApiResponse(0,"",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public mJwtData  getUserDetailsForJwtToken(String email) {

        String query="select user_id,username,email,password,profile_picture_url,token_expiry,last_login from users where Lower(email)=Lower('"+email+"') and is_active=true";
        mJwtData jwtData= jdbctemplate.queryForObject(query, new JwtRowMapper());
        return jwtData;
    }

    @Override
    public mUsers.GetUserForAuthentication loadUserDetailsByUserName(String email) {
        String query="select username,email,password from users where Lower(email)=Lower('"+email+"') and is_active=true";
        try{
            mUsers.GetUserForAuthentication user=  jdbctemplate.queryForObject(query, new UserDetailsRowMapper());
            return  user;
        }catch (Exception ex){
            return null;
        }

    }
    @Override
    public  mGeneric.mApiResponse signIn(mLogin userLogin){
        String query = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
        int user = jdbctemplate.queryForObject(query, Integer.class, userLogin.getEmail(), userLogin.getPassword());

        if (user > 0) {
            return new mGeneric.mApiResponse(1,"User Login Successfully",userLogin);
        } else {
            return new mGeneric.mApiResponse(0,"Invalid User Login and Password",null);
        }
    }
}
