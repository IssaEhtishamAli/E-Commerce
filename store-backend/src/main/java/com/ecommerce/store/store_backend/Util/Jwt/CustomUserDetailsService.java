package com.ecommerce.store.store_backend.Util.Jwt;

import com.ecommerce.store.store_backend.Models.Users.mUsers;
import com.ecommerce.store.store_backend.Repositriy.Auth.IUserRepositriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepositriy userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch user from the repository
        mUsers.GetUserForAuthentication user = userRepository.loadUserDetailsByUserName(email);
        if (user != null) {
            // Fetch roles for the user
            List<String> roles = userRepository.findRolesByUserId(user.getUserId());
            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // Return user with roles
            return new User(user.getUserName(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User Name Not Found !!");
        }
    }
}
