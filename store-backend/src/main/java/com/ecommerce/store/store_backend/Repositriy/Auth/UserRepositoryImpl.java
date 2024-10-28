package com.ecommerce.store.store_backend.Repositriy.Auth;

import com.ecommerce.store.store_backend.Config.UserRepositoryException;
import com.ecommerce.store.store_backend.Mappers.JwtRowMapper;
import com.ecommerce.store.store_backend.Mappers.UserDetailsRowMapper;
import com.ecommerce.store.store_backend.Models.Auth.mJwtData;
import com.ecommerce.store.store_backend.Models.Auth.mLogin;
import com.ecommerce.store.store_backend.Models.Auth.mSignUp;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Users.mUsers;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.slf4j.Logger;
@Repository
public class UserRepositoryImpl implements IUserRepositriy {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String INSERT_USER_ROLE = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
    private final String INSERT_USER = "INSERT INTO users (username, email, password, first_name, last_name, phone_number, profile_picture_url, address, is_active, dob, gender) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING user_id";
    private final String SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private final String SELECT_USERS = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
    private final String SELECT_ROLES_BY_USER_ID = "SELECT r.role_name FROM roles r JOIN user_roles ur ON r.role_id = ur.role_id WHERE ur.user_id = ?";

    @Override
    public mGeneric.mApiResponse saveUser(mSignUp.SignUp user, Integer roleId) {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));

            KeyHolder keyHolder = new GeneratedKeyHolder();
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement pst = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, user.getUserName());
                pst.setString(2, user.getEmail());
                pst.setString(3, user.getPassword());
                pst.setString(4, user.getFirstName());
                pst.setString(5, user.getLastName());
                pst.setString(6, user.getPhoneNumber());
                pst.setString(7, user.getProfilePicture());
                pst.setString(8, user.getAddress());
                pst.setBoolean(9, user.isIsActive());
                pst.setDate(10, Date.valueOf(user.getDob()));
                pst.setString(11, user.getGender());
                return pst;
            }, keyHolder);

            if (rowsAffected > 0) {
                int userId = keyHolder.getKey().intValue();
                int roleAssigned = jdbcTemplate.update(INSERT_USER_ROLE, userId, roleId);

                if (roleAssigned > 0) {
                    logger.info("User created successfully: userId={}, roleId={}", userId, roleId);
                    return new mGeneric.mApiResponse<>(1, "User Created and Role Assigned Successfully", HttpStatus.OK);
                } else {
                    logger.warn("Failed to assign role for userId={}", userId);
                    throw new UserRepositoryException("Failed to assign role");
                }
            } else {
                logger.error("Failed to save user: {}", user);
                throw new UserRepositoryException("Failed to save user");
            }
        } catch (UserRepositoryException ex) {
            logger.error("UserRepositoryException: {}", ex.getMessage());
            return new mGeneric.mApiResponse(0, "Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            logger.error("Unexpected error occurred while saving user: {}", ex.getMessage(), ex);
            return new mGeneric.mApiResponse(0, "Unexpected Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public mGeneric.mApiResponse<mUsers.users> findByEmail(String email) {
        try {
            mUsers.users user = jdbcTemplate.queryForObject(SELECT_BY_EMAIL, new Object[]{email}, new BeanPropertyRowMapper<>(mUsers.users.class));
            if (user == null) {
                logger.warn("User not found for email: {}", email);
                return new mGeneric.mApiResponse(0, "User not found!", HttpStatus.NOT_FOUND);
            } else {
                logger.info("User found for email: {}", email);
                return new mGeneric.mApiResponse(1, "", HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("Error finding user by email '{}': {}", email, ex.getMessage(), ex);
            return new mGeneric.mApiResponse(0, "", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public mJwtData getUserDetailsForJwtToken(String email) {
        String SELECT_USER_DETAILS = "SELECT user_id, username, email, password, profile_picture_url, last_login, token_expiry FROM users WHERE LOWER(email) = LOWER(?) AND is_active = true";
        try {
            mJwtData jwtData = jdbcTemplate.queryForObject(SELECT_USER_DETAILS, new Object[]{email}, new JwtRowMapper());
            logger.info("Retrieved JWT data for email: {}", email);
            return jwtData;
        } catch (Exception ex) {
            logger.error("Error retrieving user details for JWT: {}", ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public mUsers.GetUserForAuthentication loadUserDetailsByUserName(String email) {
        String query = "SELECT username, email, password FROM users WHERE LOWER(email) = LOWER(?) AND is_active = true";
        try {
            mUsers.GetUserForAuthentication user = jdbcTemplate.queryForObject(query, new Object[]{email}, new UserDetailsRowMapper());
            logger.info("Loaded user details for email: {}", email);
            return user;
        } catch (Exception ex) {
            logger.error("Error loading user details for email '{}': {}", email, ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public mGeneric.mApiResponse signIn(mLogin userLogin) {
        try {
            int userCount = jdbcTemplate.queryForObject(SELECT_USERS, Integer.class, userLogin.getEmail(), userLogin.getPassword());

            if (userCount > 0) {
                logger.info("User login successful for email: {}", userLogin.getEmail());
                return new mGeneric.mApiResponse(1, "User Login Successfully", userLogin);
            } else {
                logger.warn("Invalid login attempt for email: {}", userLogin.getEmail());
                return new mGeneric.mApiResponse(0, "Invalid User Login and Password", null);
            }
        } catch (Exception ex) {
            logger.error("Error during user sign-in: {}", ex.getMessage(), ex);
            return new mGeneric.mApiResponse(0, "Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<String> findRolesByUserId(Integer userId) {
        try {
            List<String> roles = jdbcTemplate.queryForList(SELECT_ROLES_BY_USER_ID, new Object[]{userId}, String.class);
            logger.info("Roles retrieved for userId: {}", userId);
            return roles;
        } catch (Exception ex) {
            logger.error("Error finding roles by user ID: {}", ex.getMessage(), ex);
            return null;
        }
    }

    @Override
    public mGeneric.mApiResponse assignRole(Integer userId, Integer roleId) {
        try {
            int rowsAffected = jdbcTemplate.update(INSERT_USER_ROLE, userId, roleId);
            if (rowsAffected > 0) {
                logger.info("Role assigned successfully for userId: {}", userId);
                return new mGeneric.mApiResponse<>(1, "Role Assigned Successfully", HttpStatus.OK);
            } else {
                logger.warn("Failed to assign role for userId: {}", userId);
                return new mGeneric.mApiResponse(0, "Failed to assign role");
            }
        } catch (Exception ex) {
            logger.error("Error assigning role to userId {}: {}", userId, ex.getMessage(), ex);
            return new mGeneric.mApiResponse(0, "Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}