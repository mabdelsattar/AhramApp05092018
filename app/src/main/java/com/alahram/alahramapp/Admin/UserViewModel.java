package com.alahram.alahramapp.Admin;

/**
 * Created by codelab on 5/13/2018.
 */

public class UserViewModel {

    public String UserName;
    public String Password;
    public int Role;
    public int UserId;
    public String FullName;

    public UserViewModel(String userName, String password, int role, int userId,String fullName) {
        UserName = userName;
        Password = password;
        Role = role;
        UserId = userId;
        FullName = fullName;
    }

    public UserViewModel() {
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
