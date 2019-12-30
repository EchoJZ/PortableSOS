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
import com.example.dontscare.FirstFragment;
import com.example.dontscare.MainActivity;
import com.example.dontscare.R;
import com.gyf.immersionbar.ImmersionBar;

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

public class begin_login extends AppCompatActivity implements View.OnClickListener{

    EditText phone;
    EditText password;
    String string_phone;
    String string_password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_login);
        ImmersionBar.with(this)
                .init();

        phone=(EditText) findViewById(R.id.begin_login_phone);
        password=(EditText) findViewById(R.id.begin_login_password);
        //登录按钮点击事件
        login=(Button) findViewById(R.id.begin_login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.begin_login:
                string_phone=phone.getText().toString();
                string_password=password.getText().toString();
//                Toast.makeText(begin_login.this,string_phone+" "+string_password,Toast.LENGTH_SHORT).show();
                //通过okhttp发起post请求
                postRequest(string_phone,string_password);
                break;
        }
    }

    /**
     * post请求后台
     * @param phone
     * @param password
     */
    private void postRequest(String phone,String password)  {
        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new FormBody.Builder()
                .add("email",phone)
                .add("password",password)
                .build();

        //发起请求 .addHeader("receipt", CommonParameter.login_receipt)
        final Request request = new Request.Builder()
                .url(CommonParameter.url_root+"/account/login")
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
                        String receipt="";//***凭证
                        if (response.code() >= 200 && response.code() < 300) {
                            String responseData="";
                            responseData=response.body().string();
                            System.out.println(responseData);
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = new JSONObject(responseData);
                                errCode = jsonObject.getInt("errCode");
                                errMsg=jsonObject.getString("errMsg");
                                receipt = jsonObject.getString("data");
                                if(errCode==0){
                                    Looper.prepare();
                                    Toast.makeText(begin_login.this,"登录成功！欢迎来到掌心SOS",Toast.LENGTH_SHORT).show();
                                    //向碎片传递用户receipt
                                    CommonParameter.receipt=receipt;
                                    Intent i=new Intent(begin_login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    Looper.loop();
                                }
                                else if(errCode==1){
                                    Looper.prepare();
                                    Toast.makeText(getBaseContext(),errMsg,Toast.LENGTH_SHORT).show();
                                    Looper.loop();
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
     * 解析返回数据,确认登录状态
     */
//    private void parseJSONWithJSONObject(String jsonData){
//        try{
//            JSONObject jsonObject=new JSONObject(jsonData);
//            errCode = jsonObject.getString("errCode");
//            int code=Integer.parseInt(errCode);
//            if(code==0){
//                Toast.makeText(getBaseContext(),"成功登录！",Toast.LENGTH_SHORT).show();
//                //向碎片传递用户receipt
//                FirstFragment fragment=new FirstFragment();
//                Bundle bundle=new Bundle();
//                bundle.putString("receipt", "");
//                fragment.setArguments(bundle);
//                Intent i=new Intent(this, MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//            else{
//                String errMsg = jsonObject.getString("errMsg");
//                Log.d("begin_login",errMsg);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}


