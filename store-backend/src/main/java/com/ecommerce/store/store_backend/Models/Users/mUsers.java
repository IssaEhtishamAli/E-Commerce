package com.ecommerce.store.store_backend.Models.Users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


public class mUsers {
    @Data
    @NoArgsConstructor
    public static class users{
        private int userId;
        private String UserName;
        private String Email;
        private String Password;
        private String FirstName;
        private String LastName;
        private String PhoneNumber;
        private String Gender;
        private String Dob;
        private String ProfilePictureUrl;
        private String Address;
        private Timestamp CreatedAt;
        private Timestamp UpdatedAt;
        private Timestamp LastLogin;
        private boolean IsActive;
        private Timestamp TokenExpiry;

        public users(int userId, String userName, String email, String password, String firstName, String lastName, String phoneNumber, String gender, String dob,String profilePictureUrl, String address, Timestamp createdAt, Timestamp updatedAt, Timestamp lastLogin, boolean isActive, Timestamp tokenExpiry) {
            this.userId = userId;
            UserName = userName;
            Email = email;
            Password = password;
            FirstName = firstName;
            LastName = lastName;
            PhoneNumber = phoneNumber;
            Gender = gender;
            Dob = dob;
            ProfilePictureUrl = profilePictureUrl;
            Address = address;
            CreatedAt = createdAt;
            UpdatedAt = updatedAt;
            LastLogin = lastLogin;
            IsActive = isActive;
            TokenExpiry = tokenExpiry;
        }
    }
    @Data
    @NoArgsConstructor
    public static  class GetProfilePersonalInfo{
        private String Name;
        private String Email;
        private String Phone;
        private String Password;
        private String Profile_Picture;

        public GetProfilePersonalInfo(String name, String email, String phone, String password, String profile_Picture) {
            Name = name;
            Email = email;
            Phone = phone;
            Password = password;
            Profile_Picture = profile_Picture;
        }
    }
    @Data
    @NoArgsConstructor
    public static  class UpdateProfilePersonalInfo{
        private  int Id;
        private String Name;
        private String Email;
        private String Phone;
        private String ProfilePicture;

        public UpdateProfilePersonalInfo(int id, String name, String email, String phone, String profilePicture) {
            Id = id;
            Name = name;
            Email = email;
            Phone = phone;
            ProfilePicture = profilePicture;
        }
    }
    @Data
    @NoArgsConstructor
    public  static class  UpdatePasswordInfo{
        private  int Id;
        private String CurrentPassword;
        private String NewPassword;

        public UpdatePasswordInfo(int id, String currentPassword, String newPassword) {
            Id = id;
            CurrentPassword = currentPassword;
            NewPassword = newPassword;
        }
    }
    @Data
    public static class GetUserForAuthentication {
        private String Email;
        private String Password;
        private String UserName;

        public GetUserForAuthentication(String email, String password, String userName) {
            Email = email;
            Password = password;
            UserName = userName;
        }

        public GetUserForAuthentication() {
        }
    }
}
