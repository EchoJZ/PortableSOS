package com.example.dontscare.thirdfragmentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dontscare.R;
import com.gyf.immersionbar.ImmersionBar;

import mehdi.sakout.aboutpage.AboutPage;

public class home_about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.from_right, R.anim.no_slide);//设置滑动动画
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.head30)
                .setDescription("掌心SOS由5位大学生共同开发，旨在保护女性安全。希望我们共同的努力可以让世界变得有点点不一样。  The palm SOS was jointly developed by five college students to protect women's safety. We hope that our joint efforts can make the world a little different.")
                .addGroup("Connect with us")
                .addEmail("elmehdi.sakout@gmail.com")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("medyo")
                .create();
        setContentView(aboutPage);
        ImmersionBar.with(this)
                .init();
//
//        ImageView title_return=(ImageView) findViewById(R.id.home_about_return);
//        title_return.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_slide, R.anim.out_right);
    }
}
