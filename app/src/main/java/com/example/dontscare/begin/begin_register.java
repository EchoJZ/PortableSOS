package com.example.dontscare.begin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dontscare.CommonParameter;
import com.example.dontscare.R;
import com.example.dontscare.thirdfragmentactivity.home_site;
import com.gyf.immersionbar.ImmersionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class begin_register extends AppCompatActivity implements View.OnClickListener{

    EditText phone;
    EditText password;
    EditText name;
    EditText vCode;
    String string_phone;
    String string_password;
    String string_name;
    String string_vCode;
    Button register;
    Button getVcode;

    private String verificationCode;

    private void setVerificationCode(){
        Random random = new Random();
        verificationCode = "";
        for(int i = 0; i < 6; i++){
            verificationCode += random.nextInt(10);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_register);
        ImmersionBar.with(this)
                .init();

        phone=(EditText) findViewById(R.id.begin_register_phone);
        password=(EditText) findViewById(R.id.begin_register_password);
        name=(EditText) findViewById(R.id.begin_register_name);
        vCode=(EditText) findViewById(R.id.begin_register_verification);
        //登录按钮点击事件
        register=(Button) findViewById(R.id.begin_register);
        register.setOnClickListener(this);
        //获取验证码按钮点击事件
        getVcode=(Button) findViewById(R.id.begin_register_getVcode);
        getVcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.begin_register_getVcode:
                Log.d("begin_register_getVcode", "点击了获取验证码按钮");
                string_phone=phone.getText().toString();
                setVerificationCode();
                try{
                    sendVerificationCode(string_phone); //发送验证码
                    Toast.makeText(getBaseContext(), "验证码已发送", Toast.LENGTH_LONG).show();
                } catch(Exception e){

                    Toast.makeText(getBaseContext(), "验证码发送失败", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


                break;
            case R.id.begin_register:
                Log.d("begin_register","点击了注册按钮");
                string_name=name.getText().toString();
                string_phone=phone.getText().toString();
                string_password=password.getText().toString();
                string_vCode=vCode.getText().toString();
                if(string_vCode.equals(verificationCode)){ //验证码和输入一致
                    // Toast.makeText(getBaseContext(),"验证成功",Toast.LENGTH_LONG).show();
                    postRequest(string_name,string_phone,string_password);
                }else{
                    Toast.makeText(getBaseContext(), "验证码错误", Toast.LENGTH_LONG).show();
                }
//                Toast.makeText(this,string_name+string_phone+" "+string_password,Toast.LENGTH_SHORT).show();
                //通过okhttp发起post请求
                break;
        }
    }

    /**
     * post请求后台
     * @param phone
     * @param password
     */
    private void postRequest(String name,String phone,String password)  {
        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new FormBody.Builder()
                .add("user_name",name)
                .add("email",phone)
                .add("password",password)
                .build();

        //发起请求
//      .addHeader("receipt", comParam.register_receipt)
        final Request request = new Request.Builder()
                .url(CommonParameter.url_root+"/account/regist")
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
                                String reponseData=response.body().string();
                                System.out.println("打印注册返回结果："+reponseData);
                                JSONObject jsonObject= null;
                                try {
                                    jsonObject = new JSONObject(reponseData);
                                    errCode = jsonObject.getInt("errCode");
                                    errMsg=jsonObject.getString("errMsg");
                                    if(errCode==0){
                                        Looper.prepare();
                                        Toast.makeText(getBaseContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                                        //跳转至登录界面
                                        Intent i=new Intent(begin_register.this, begin_login.class);
                                        startActivity(i);
                                        finish();
                                        Looper.loop();

                                    }else if(errCode==1){
                                        Looper.prepare();
                                        Toast.makeText(getBaseContext(),errMsg,Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                } catch (JSONException e) {
                                    Looper.prepare();
                                    e.printStackTrace();
                                    Toast.makeText(getBaseContext(),"注册失败！",Toast.LENGTH_SHORT).show();
                                    Looper.loop();

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

    //发送验证码
    private void sendVerificationCode(final String email) {
        try {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        SendEmail se = new SendEmail(email);
                        se.sendTextEmail(verificationCode);//发送html邮件
                        Looper.prepare();
                        Toast.makeText(getBaseContext(),"发送成功",Toast.LENGTH_LONG).show();
                        Looper.loop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断输入的验证码是否正确
    private void judgeVerificationCode() {
        if(vCode.getText().toString().equals(verificationCode)){ //验证码和输入一致
            Toast.makeText(getBaseContext(),"验证成功",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getBaseContext(), "验证码错误", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 解析返回数据
     */
//    private void parseJSONWithJSONObject(String jsonData){
//        try{
//            JSONObject jsonObject=new JSONObject(jsonData);
//            errCode = jsonObject.getString("errCode");
//            ifLoginSuccess(errCode);
//            String errMsg = jsonObject.getString("errMsg");
//            Log.d("begin_login",errMsg);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    /**
     * 确认是否注册成功，并跳转至登录界面
     */
//    private void ifLoginSuccess(int code){
//        if(code==0){
//            Toast.makeText(getBaseContext(),"成功注册！",Toast.LENGTH_SHORT).show();
//            //跳转至登录界面
//            Intent i=new Intent(this, begin_login.class);
//            startActivity(i);
//            finish();
//        }
//        else
//            Toast.makeText(getBaseContext(),"注册失败！",Toast.LENGTH_SHORT).show();
//
//
//    }
}
