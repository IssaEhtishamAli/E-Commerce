package com.ecommerce.store.store_backend.Services.Auth;

import com.ecommerce.store.store_backend.Models.Auth.mJwtData;
import com.ecommerce.store.store_backend.Models.Auth.mLogin;
import com.ecommerce.store.store_backend.Models.Auth.mSignUp;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Users.mUsers;
import com.ecommerce.store.store_backend.Repositriy.Auth.IUserRepositriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements  IUserService{
    @Autowired
    private IUserRepositriy userRepositriy;
    @Override
    public mGeneric.mApiResponse saveUser(mSignUp.SignUp user) {
        return userRepositriy.saveUser(user);
    }

    @Override
    public mGeneric.mApiResponse<mUsers.users> findByEmail(String email) {
        return userRepositriy.findByEmail(email);
    }

    @Override
    public mJwtData getUserDetailsForJwtToken(String email) {
        return userRepositriy.getUserDetailsForJwtToken(email);
    }
    @Override
    public mGeneric.mApiResponse signIn(mLogin userLogin){
        return userRepositriy.signIn(userLogin);
    }
}
