package com.dimitarrradev.photoapp.api.users.model;

public class LoginRequestModel {
    private String email;
    private String password;

    public LoginRequestModel() {
    }

    public LoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
