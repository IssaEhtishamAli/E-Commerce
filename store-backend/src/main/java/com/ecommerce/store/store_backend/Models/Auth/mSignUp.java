package com.ecommerce.store.store_backend.Models.Auth;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class mSignUp {
    @Data
    @NoArgsConstructor
    public static class SignUp{
        private String UserName;
        private String FirstName;
        private String LastName;
        private String Email;
        private String PhoneNumber;
        private String Password;
        private String ProfilePicture;
        private String Address;
        private LocalDate Dob;
        private String Gender;
        private boolean IsActive;

        public SignUp(String userName, String firstName, String lastName, String email,LocalDate dob,String gender, String phoneNumber, String password, String profilePicture, String address, boolean isActive) {
            UserName = userName;
            FirstName = firstName;
            LastName = lastName;
            Email = email;
            PhoneNumber = phoneNumber;
            Password = password;
            ProfilePicture = profilePicture;
            Address = address;
            IsActive = isActive;
            Dob =dob;
            Gender = gender;
        }
    }
}
