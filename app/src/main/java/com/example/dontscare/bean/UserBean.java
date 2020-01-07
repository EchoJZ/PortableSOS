package com.example.dontscare.bean;

public class UserBean {
    String user_id;
    String phone;
    String email;
    String user_name;
    String user_intro;
    String password;
    String avatar;
    String location;
    String update_time;
    String security_status;


    public UserBean(String user_id, String phone, String email, String user_name,
                    String user_intro, String password, String avatar, String location,
                    String update_time, String security_status) {
        this.user_id = user_id;
        this.phone = phone;
        this.email = email;
        this.user_name = user_name;
        this.user_intro = user_intro;
        this.password = password;
        this.avatar = avatar;
        this.location = location;
        this.update_time = update_time;
        this.security_status = security_status;
    }

    public UserBean(){}

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_intro() {
        return user_intro;
    }

    public void setUser_intro(String user_intro) {
        this.user_intro = user_intro;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getSecurity_status() {
        return security_status;
    }

    public void setSecurity_status(String security_status) {
        this.security_status = security_status;
    }
}
