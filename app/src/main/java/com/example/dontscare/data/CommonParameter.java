package com.example.dontscare.data;

import com.example.dontscare.bean.ContactBean;
import com.example.dontscare.bean.UserBean;

import java.util.ArrayList;

public class CommonParameter {
    public static String url_root = "http://119.29.162.56:8080";
    public static String receipt = "";

    //UserBean
    public static String user_id = "";
    public static String phone = "";
    public static String email = "";
    public static String user_name = "";
    public static String user_intro = "";
    public static String password = "";
    public static String avatar = "";
    public static String location = "";
    public static String update_time = "";
    public static String security_status = "";

    public static ArrayList<ContactBean> list = new ArrayList<ContactBean>();
    public static UserBean user = new UserBean();

    public static void clearUserList(){
        list.clear();
    }

    public static void clearAllData(){
        user_id = "";
        phone = "";
        email = "";
        user_name = "";
        user_intro = "";
        password = "";
        avatar = "";
        location = "";
        update_time = "";
        security_status = "";
        list.clear();
    }

    public static void updateUserInfo(String name,String pwd,String intro){
        user_name = name;
        password = pwd;
        user_intro = intro;
    }

    public static void changeSecurityStatus(){
        if("0".equals(security_status) ){
            security_status = "1";
        }else {
            security_status = "0";
        }
    }

}
