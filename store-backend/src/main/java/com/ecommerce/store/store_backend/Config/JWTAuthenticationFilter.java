package com.ecommerce.store.store_backend.Config;

import com.ecommerce.store.store_backend.Models.Auth.mJwtData;
import com.ecommerce.store.store_backend.Util.Jwt.JwtTokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtutil;

    @Autowired
    private mJwtData UserInfoObj;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        // Log request for debugging
        System.out.println("Request received: " + request.getRequestURI());

        String requestTokenHeader = request.getHeader("Authorization");
        String email = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            try {
                // Check if the token is expired
                boolean IsExpired = jwtutil.isTokenExpired(jwtToken);

                if (!IsExpired) {
                    // Extract user information from JWT token
                    Object UserInfo = jwtutil.extractAllClaims(jwtToken).get("UserInfo");
                    HashMap<String, Object> UserInfoMap = (HashMap<String, Object>) UserInfo;

                    // Set user details from the token claims
                    UserInfoObj.setEmail(UserInfoMap.get("email").toString());
                    UserInfoObj.setPassword(UserInfoMap.get("password").toString());
                    UserInfoObj.setUserName(UserInfoMap.get("userName").toString());
                    UserInfoObj.setUserId(Integer.parseInt(UserInfoMap.get("userId").toString()));

                    email = jwtutil.extractUsername(jwtToken);

                    // Generate a new token and set it in the response headers
                    String NewToken = jwtutil.generateToken(UserInfoObj);
                    response.setHeader("Access-Control-Expose-Headers", "NewToken");
                    response.setHeader("NewToken", NewToken);

                    // Log token renewal
                    System.out.println("New JWT token generated for: " + email);
                }

            } catch (Exception e) {
                // Handle any exceptions and log the error
                System.err.println("Error while processing JWT token: " + e.getMessage());
                e.printStackTrace();
            }

            // Authenticate user if email is extracted from the token
            UserDetails userDetails = new User("user@domain.com", "", new ArrayList<>());
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                // Log successful authentication
                System.out.println("Authenticated user: " + email);
            } else {
                // Log token validation failure
                System.out.println("JWT token validation failed");
            }
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
