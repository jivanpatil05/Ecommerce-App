package com.example.ecommerce.Response;

import com.example.ecommerce.ModelClass.User.Users;

public class UserResponse {
    private int code;
    private String meta;
    private Users data;

    public UserResponse(int code, String meta, Users data) {
        this.code = code;
        this.meta = meta;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public Users getData() {
        return data;
    }

    public void setData(Users data) {
        this.data = data;
    }
}
