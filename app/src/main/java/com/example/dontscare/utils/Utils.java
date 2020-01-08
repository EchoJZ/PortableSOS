package com.example.dontscare.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.dontscare.begin.LoginActivity;
import com.example.dontscare.data.CommonParameter;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Utils {

    /**
     * 同步登陆请求
     * @param phone
     * @param password
     */
    public static void login(final Handler handler, String phone, String password)  {
        RequestBody formBody = new FormBody.Builder()
                .add("email",phone)
                .add("password",password)
                .build();

        final Request request = new Request.Builder()
                .url(CommonParameter.url_root+"/account/login")
                .post(formBody)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClientclient = new OkHttpClient();
                    Call call = okHttpClientclient.newCall(request);
                    Response response = call.execute();
                    String responseData = response.body().string();

                    JSONObject jsonObject = new JSONObject(responseData);
                    int errCode = jsonObject.getInt("errCode");
                    String errMsg = jsonObject.getString("errMsg");
                    String receipt = jsonObject.getString("data");

                    Message msg = new Message();
                    if(errCode == 0){
                        CommonParameter.receipt=receipt;
                        msg.obj = "success";
                        handler.sendMessage(msg);
                    }else{
                        msg.obj = errMsg;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
    ***获取全部的个人信息
    **/
    public static void getUserInfo(final Handler handler, String receipt){

        RequestBody formBody = new FormBody.Builder()
                .build();

        final Request request = new Request.Builder()
                .url(CommonParameter.url_root+"/user/me")
                .addHeader("receipt", CommonParameter.receipt)
                .post(formBody)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e.getMessage());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            int errCode = -1;
                            String errMsg = "";

                            if (response.code() >= 200 && response.code() < 300) {
                                String responseData="";
                                responseData=response.body().string();
                                System.out.println(responseData);
                                JSONObject jsonObject= null;
                                try {
                                    jsonObject = new JSONObject(responseData);
                                    errCode = jsonObject.getInt("errCode");
                                    errMsg=jsonObject.getString("errMsg");
                                    Message msg = new Message();
                                    if(errCode == 0){
//                                        user.setUser_id(jsonObject.getString("user_id"));
//                                        user.setPhone((jsonObject.getString("phone")));
//                                        user.setEmail(jsonObject.getString("email"));
//                                        user.setUser_name(jsonObject.getString("user_name"));
//                                        user.setUser_intro(jsonObject.getString("user_intro"));
//                                        user.setPassword(jsonObject.getString("password"));
//                                        user.setAvatar(jsonObject.getString("avatar"));
//                                        user.setLocation(jsonObject.getString("location"));
//                                        user.setUpdate_time(jsonObject.getString("update_time"));
//                                        user.setSecurity_status(jsonObject.getString("security_status"));
                                        JSONObject jsonObject_info = jsonObject.optJSONObject("data");
                                        CommonParameter.user_name = jsonObject_info.getString("user_name");
                                        CommonParameter.phone = jsonObject_info.getString("phone");
                                        CommonParameter.email = jsonObject_info.getString("email");
                                        CommonParameter.user_name = jsonObject_info.getString("user_name");
                                        CommonParameter.user_intro = jsonObject_info.getString("user_intro");
                                        CommonParameter.password = jsonObject_info.getString("password");
                                        CommonParameter.avatar = jsonObject_info.getString("avatar");
                                        CommonParameter.location = jsonObject_info.getString("location");
                                        CommonParameter.update_time = jsonObject_info.getString("update_time");
                                        CommonParameter.security_status = jsonObject_info.getString("security_status");

                                        msg.obj = "success";
                                        handler.sendMessage(msg);
                                    }else if(errCode == 1){
                                        msg.obj = "fail";
                                        handler.sendMessage(msg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     ***修改个人信息
     **/
    public static void modifyUserInfo(final Handler handler, String name, String password, String intro)  {
        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new FormBody.Builder()
                .add("user_name",name)
                .add("password",password)
                .add("user_intro",intro)
                .add("avatar","")//此项待实现
                .build();

        //发起请求
        final Request request = new Request.Builder()
                .url(CommonParameter.url_root+"/user/modify")
                .addHeader("receipt", CommonParameter.receipt)
                .post(formBody)
                .build();
        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e.getMessage());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            int errCode=-1;
                            String errMsg="";
                            if (response.code() >= 200 && response.code() < 300) {
                                String responseData = response.body().string();
                                System.out.println(responseData);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(responseData);
                                    errCode = jsonObject.getInt("errCode");
                                    errMsg=jsonObject.getString("errMsg");
                                    Message msg = new Message();
                                    if(errCode==0){
                                        msg.obj = "success";
                                        handler.sendMessage(msg);
                                    }
                                    else if(errCode==1){
                                        msg.obj = "fail";
                                        handler.sendMessage(msg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
    ***更新用户安全状态
    **/
    public static void updateSecurityStatus(final Handler handler, String security_status)  {
        if("0".equals(security_status)){
            security_status = "1";
        }else{
            security_status = "0";
        }
        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new FormBody.Builder()
                .add("security_status",security_status)
                .build();
        Log.d("Utils.test",security_status);
        Log.d("Utils.test",String.valueOf(formBody));

        //发起请求
        final Request request = new Request.Builder()
                .url(CommonParameter.url_root+"/user/security_update")
                .addHeader("receipt", CommonParameter.receipt)
                .post(formBody)
                .build();
        Log.d("Utils.test",String.valueOf(request));

        //新建一个线程，用于得到服务器响应的参数
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e.getMessage());
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            int errCode=-1;
                            String errMsg="";
                            if (response.code() >= 200 && response.code() < 300) {
                                String responseData = response.body().string();
                                System.out.println(responseData);
                                JSONObject jsonObject = null;
                                Message msg = new Message();
                                try {
                                    jsonObject = new JSONObject(responseData);
                                    errCode = jsonObject.getInt("errCode");
                                    errMsg=jsonObject.getString("errMsg");
                                    if(errCode==0){
                                        CommonParameter.changeSecurityStatus();
                                        msg.obj = "success";
                                        handler.sendMessage(msg);
                                    }
                                    else if(errCode==1){
                                        Log.d("Utils.java",errMsg+"\n");
                                        msg.obj = "fail";
                                        handler.sendMessage(msg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
