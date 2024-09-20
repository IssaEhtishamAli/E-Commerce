package com.ecommerce.store.store_backend.Controllers;

import com.ecommerce.store.store_backend.Models.Auth.mJwtData;
import com.ecommerce.store.store_backend.Models.Auth.mJwtResponse;
import com.ecommerce.store.store_backend.Models.Auth.mLogin;
import com.ecommerce.store.store_backend.Models.Auth.mSignUp;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Services.Auth.IUserService;
import com.ecommerce.store.store_backend.Util.Jwt.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth/")
public class AuthController {
    @Autowired
    private IUserService userService;
    @Autowired
    private JwtTokenUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Create a new User", description = "Adds a new user to the e-commerce store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "User creation failed", content = @Content)
    })
    @RequestMapping(value = "signUp",method = RequestMethod.POST)
    public ResponseEntity<?> signUp(mSignUp.SignUp signUp){
        try {
            mGeneric.mApiResponse response = userService.saveUser(signUp);
            if (response.getRespCode() == 1) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "SignIn User", description ="Signin user to the e-commerce store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Signin successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "User Signin failed", content = @Content)
    })
    @RequestMapping(value = "signIn",method = RequestMethod.POST)
    public ResponseEntity<?> signIn(mLogin signinBody){
        try {
            Authentication AuthResp = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinBody.getEmail(),signinBody.getPassword()));
            mJwtData UserInfo = userService.getUserDetailsForJwtToken(signinBody.getEmail());
            UserInfo.setEmail(signinBody.getEmail());
            String picture=UserInfo.getProfilePicture();
            if(UserInfo !=null){
                String Token=this.jwtUtil.generateToken(UserInfo);
                mJwtResponse response= new mJwtResponse(Token,UserInfo.getUserId(),UserInfo.getUserName(),UserInfo.getProfilePicture(), UserInfo.getLastLogin(),UserInfo.getTokenExpiry());
                return ResponseEntity.ok(new mGeneric.mApiResponse(1,"User Login Successfully",response));
            }
            else {
                return new ResponseEntity<>(new mGeneric.mApiResponse<>(0, "User Not Found",""), HttpStatus.NOT_FOUND);
            }
        }catch (BadCredentialsException e){
            ErrorResponseException errorResponse = new ErrorResponseException(HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorResponseException errorResponse = new ErrorResponseException(HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
