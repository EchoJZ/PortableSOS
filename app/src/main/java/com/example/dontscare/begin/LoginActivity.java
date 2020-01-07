package com.example.dontscare.begin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.dontscare.R;
import com.example.dontscare.app.MainActivity;
import com.example.dontscare.data.CommonParameter;
import com.example.dontscare.utils.Utils;
import com.gyf.immersionbar.ImmersionBar;

public class LoginActivity extends AppCompatActivity{

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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string_phone=phone.getText().toString();
                string_password=password.getText().toString();
                Utils.login(handler,string_phone,string_password);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if("success".equals(msg.obj)){
                Toast.makeText(LoginActivity.this, "登录成功！",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);//                    清除当前栈中的所有活动
//                    清除当前栈中的所有活动
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else {
                Toast.makeText(LoginActivity.this, ""+ msg.obj,Toast.LENGTH_LONG).show();
                Log.d("LoginActivity",""+msg.obj);
            }
        }
    };

}


