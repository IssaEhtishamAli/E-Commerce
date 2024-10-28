package com.ecommerce.store.store_backend.Services.Auth;


import com.ecommerce.store.store_backend.Models.Auth.mJwtData;
import com.ecommerce.store.store_backend.Models.Auth.mLogin;
import com.ecommerce.store.store_backend.Models.Auth.mSignUp;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Users.mUsers;

import java.util.List;

public interface IUserService {
    mGeneric.mApiResponse  saveUser(mSignUp.SignUp user, Integer roleId);
    mGeneric.mApiResponse<mUsers.users> findByEmail(String email);
    mJwtData getUserDetailsForJwtToken(String email);
    mGeneric.mApiResponse signIn(mLogin userLogin);
    List<String> findRolesByUserId(Integer userId);
    mGeneric.mApiResponse assignRole(Integer userId, Integer roleId);
}
