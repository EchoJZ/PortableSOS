package com.example.dontscare.ui.person;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dontscare.data.CommonParameter;
import com.example.dontscare.R;
import com.example.dontscare.begin.WelcomeActivity;
import com.gyf.immersionbar.ImmersionBar;

public class SettingActivity extends AppCompatActivity {
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.from_right, R.anim.no_slide);//设置滑动动画
        setContentView(R.layout.home_setting);
        ImmersionBar.with(this)
                .barAlpha(0.3f)
                .init();

        //标题栏
        ImageView title_return=(ImageView) findViewById(R.id.home_setting_return);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //退出app
        tv1=(TextView) findViewById(R.id.home_setting_exit) ;
        String text1=" 退出掌心SOS";
        SpannableString ss1=new SpannableString(text1);
        ss1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showCoverDialog();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 0, text1.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv1.setText(ss1);
        tv1.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /*
    **退出弹框
    */
    private void showCoverDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出掌心SOS？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CommonParameter.clearAllData();
                Intent intent=new Intent(SettingActivity.this, WelcomeActivity.class);
//                设置当前栈中所有的活动都退出
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    /**
    ***标题栏返回键
    **/
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_slide, R.anim.out_right);
    }
}
