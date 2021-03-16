package com.auth.AuthenticationService;

public class UserNameAndPasswordStore {

    String userName;
    String password;

    public UserNameAndPasswordStore() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
