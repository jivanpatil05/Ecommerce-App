package com.example.ecommerce.Response;

import com.example.ecommerce.ModelClass.Login.LoginUser;

public class LoginResponse {
    String token;
    LoginUser data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginUser getData() {
        return data;
    }

    public void setData(LoginUser data) {
        this.data = data;
    }
}
