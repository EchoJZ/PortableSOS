package com.example.dontscare.bean;

import java.util.List;

public class LoginBean {

    private String phone;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "phone='" + phone + '\'' +
                "password='"+password+ '\''+
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
