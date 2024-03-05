package com.example.ecommerce.ModelClass.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUser {
    @SerializedName("username")
    @Expose
    private Integer username;
    @SerializedName("password")
    @Expose
    private Integer password;

    public LoginUser(Integer username, Integer password) {
        this.username = username;
        this.password = password;
    }

    public LoginUser(String enteredUsername, String enteredPassword) {

    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }
}
