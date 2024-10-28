package com.ecommerce.store.store_backend.Util.Jwt;

import com.ecommerce.store.store_backend.Models.Auth.mJwtData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final long serialVersionUId = -2550185123234112312L;
    private static final long JWT_TOKEN_VALIDITY = 1800_000; // 30 minutes

    private String SECRET_KEY = "123QSDSAD132313QSDAS1323213DQWDSD123123DSDSAD123123DASD12312312SDSADASSFDS123123";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        String expiryString = (String) claims.get("token_expiry");
        LocalDateTime expiry = LocalDateTime.parse(expiryString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        mJwtData userDetails = new mJwtData();
        userDetails.setTokenExpiry(expiry);

        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {

        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(mJwtData userDetails) {
        userDetails.setProfilePicture(""); // Clear profile picture in token
        LocalDateTime currentDateTime = LocalDateTime.now();
        userDetails.setLastLogin(currentDateTime);
        long tokenValidityInSeconds = JWT_TOKEN_VALIDITY / 1000;
        LocalDateTime expiryTime = LocalDateTime.now().plusSeconds(tokenValidityInSeconds);
        userDetails.setTokenExpiry(expiryTime); // Set tokenExpiry to the calculated LocalDateTime

        String sql_query = "UPDATE users SET last_login= ?, token_expiry = ? WHERE user_id = ?";
        jdbcTemplate.update(sql_query,
                userDetails.getLastLogin() != null ? Timestamp.valueOf(userDetails.getLastLogin()) : null,
                userDetails.getTokenExpiry() != null ? Timestamp.valueOf(userDetails.getTokenExpiry()) : null,
                userDetails.getUserId());

        Map<String, Object> claims = new HashMap<>();

        // Convert LocalDateTime to String (ISO 8601 format) before adding to claims
        String formattedLastLogin = userDetails.getLastLogin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String formattedExpiry = userDetails.getTokenExpiry().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        claims.put("last_login",formattedLastLogin);
        claims.put("token_expiry", formattedExpiry); // Store formatted expiry as a String

        // Configure ObjectMapper and register JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register module for LocalDateTime support

        try {
            String userInfoJson = objectMapper.writeValueAsString(userDetails);
            claims.put("UserInfo", userInfoJson); // Add serialized userDetails as JSON string
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing UserInfo", e);
        }

        return createToken(claims, userDetails.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // JWT_TOKEN_VALIDITY is in milliseconds
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
