package com.example.dontscare.begin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dontscare.R;
import com.example.dontscare.thirdfragmentactivity.home_about;
import com.gyf.immersionbar.ImmersionBar;

/*
解决 页面跳转后原页面的销毁问题
*/
public class begin_welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.begin_welcome);
        ImmersionBar.with(this)
                .init();

        TextView tv1=(TextView) findViewById(R.id.begin_welcom_register);
        TextView tv2=(TextView) findViewById(R.id.begin_welcom_know);
        Button button=(Button) findViewById(R.id.begin_welcome_login);
        String text1="新用户注册";
        String text2="了解掌心SOS";
        SpannableString ss1=new SpannableString(text1);
        SpannableString ss2=new SpannableString(text2);
        //点击事件1:注册
        ss1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i=new Intent(begin_welcome.this, begin_register.class);
                startActivity(i);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 0, text1.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv1.setText(ss1);
        tv1.setMovementMethod(LinkMovementMethod.getInstance());
        //点击事件2：了解
        ss2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i=new Intent(begin_welcome.this, home_about.class);
                startActivity(i);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 0, text2.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv2.setText(ss2);
        tv2.setMovementMethod(LinkMovementMethod.getInstance());
        //登录
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(begin_welcome.this, begin_login.class);
                startActivity(i);
            }
        });
    }
}
