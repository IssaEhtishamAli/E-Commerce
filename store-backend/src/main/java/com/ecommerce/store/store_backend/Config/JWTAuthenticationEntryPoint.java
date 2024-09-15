package com.ecommerce.store.store_backend.Config;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Log unauthorized access attempt
        System.out.println("Unauthorized access attempt to: " + request.getRequestURI());

        // Respond with a 401 Unauthorized status
        response.sendError(401, "Not Authorized To Access This End Point");
    }
}

