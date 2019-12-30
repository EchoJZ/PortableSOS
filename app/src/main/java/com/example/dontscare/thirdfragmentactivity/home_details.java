package com.example.dontscare.thirdfragmentactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dontscare.CommonParameter;
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

public class home_details extends AppCompatActivity {

    EditText home_details_phone,home_details_user_name2,home_details_user_intro,home_details_user_password;
    TextView home_details_user_name1,home_details_location,home_details_update_time,home_details_security_status;
    Button home_details_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.from_right, R.anim.no_slide);//设置滑动动画
        setContentView(R.layout.home_details);
        ImmersionBar.with(this)
                .barAlpha(0.3f)
                .init();
        ImageView title_return=(ImageView) findViewById(R.id.home_details_return);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        home_details_user_name1=(TextView) findViewById(R.id.home_details_user_name1);
        home_details_user_name2=(EditText) findViewById(R.id.home_details_user_name2);
        home_details_phone=(EditText) findViewById(R.id.home_details_phone);
        home_details_phone.setFocusable(false);
        home_details_user_intro=(EditText) findViewById(R.id.home_details_user_intro);
        home_details_user_password=(EditText) findViewById(R.id.home_details_user_password);
        home_details_location=(TextView) findViewById(R.id.home_details_location);
        home_details_update_time=(TextView) findViewById(R.id.home_details_update_time);
        home_details_security_status=(TextView) findViewById(R.id.home_details_security_status);
        home_details_edit=(Button) findViewById(R.id.home_details_edit);


        //设置初始信息
        home_details_user_name1.setText(CommonParameter.user_name);
        home_details_user_name2.setText(CommonParameter.user_name);
        home_details_phone.setText(CommonParameter.phone);
        home_details_user_intro.setText(CommonParameter.user_intro);
        home_details_user_password.setText(CommonParameter.password);
        home_details_location.setText(CommonParameter.location);
        home_details_update_time.setText(CommonParameter.update_time);
        if(CommonParameter.security_status.equals('0')){
            home_details_security_status.setText("安全");
        }else{
            home_details_security_status.setText("不安全");
        }


        home_details_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=home_details_user_name2.getText().toString();
                String password=home_details_user_password.getText().toString();
                String introduce=home_details_user_intro.getText().toString();
                postRequest(name,password,introduce);
            }
        });
    }

    /**
    ***更新个人信息
    **/
    private void postRequest(String name,String pwd,String intro)  {
        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new FormBody.Builder()
                .add("user_name",name)
                .add("password",pwd)
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
                                String responseData="";
                                responseData=response.body().string();
                                System.out.println(responseData);
                                JSONObject jsonObject= null;
                                try {
                                    errCode = jsonObject.getInt("errCode");
                                    errMsg=jsonObject.getString("errMsg");
                                    if(errCode==0){
                                        Looper.prepare();
                                        Toast.makeText(home_details.this,"修改成功！",Toast.LENGTH_SHORT).show();
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_slide, R.anim.out_right);
    }

}
