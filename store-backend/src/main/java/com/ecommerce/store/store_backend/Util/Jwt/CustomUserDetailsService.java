package com.ecommerce.store.store_backend.Util.Jwt;

import com.ecommerce.store.store_backend.Models.Users.mUsers;
import com.ecommerce.store.store_backend.Repositriy.Auth.IUserRepositriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepositriy userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch user from the repository
        mUsers.GetUserForAuthentication user = userRepository.loadUserDetailsByUserName(email);
        if (user != null) {
            // Log successful user load
            System.out.println("Loaded user: " + email);
            return new User(user.getUserName(), user.getPassword(), new ArrayList<>());
        } else {
            // Log user not found
            System.err.println("User not found: " + email);
            throw new UsernameNotFoundException("User Name Not Found !!");
        }
    }
}
