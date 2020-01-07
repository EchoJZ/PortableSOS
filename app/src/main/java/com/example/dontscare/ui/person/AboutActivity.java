package com.example.dontscare.ui.person;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.dontscare.R;
import com.gyf.immersionbar.ImmersionBar;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.from_right, R.anim.no_slide);//设置滑动动画
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.head30)
                .setDescription("掌心SOS由6位大学生共同开发，旨在保护女性安全。希望我们共同的努力可以让世界" +
                        "变得不一样。  \n" +
                        "PortableSOS was jointly developed by five college students " +
                        "to protect women's safety. We hope that our joint efforts can make the" +
                        " world a little better.")
                .addGroup("Connect with us")
                .addEmail("deardeecho@gmail.com")
                .addPlayStore("")
                .addGitHub("EchoJZ/PortableSOS")
                .create();
        setContentView(aboutPage);
        ImmersionBar.with(this)
                .init();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_slide, R.anim.out_right);
    }
}
