package com.example.dontscare;

import android.widget.Toast;

import java.util.ArrayList;

public class CommonParameter {
    public static String url_root="http://119.29.162.56:8080";
    public static String receipt="";
    public static String user_name="";
    public static String password="";
    public static String phone="";
    public static String user_intro="";
    public static String location="";
    public static String update_time="";
    public static String security_status="";
    public static ArrayList<UserContact> list=new ArrayList<UserContact>();

    public static void clearUserList(){
        list.clear();
    }

    public static void clearAllData(){
        receipt="";
        user_name="";
        password="";
        phone="";
        user_intro="";
        location="";
        update_time="";
        security_status="";
        list.clear();
    }

//    普通碎片 Toast.makeText(getContext(),errMsg,Toast.LENGTH_SHORT).show();
//    底层碎片 Toast.makeText(getActivity(), "您点击了获取位置", Toast.LENGTH_SHORT).show();
//    活动  Toast.makeText(getBaseContext(),"注册成功！",Toast.LENGTH_SHORT).show();
}
