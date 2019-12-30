package com.example.dontscare.thirdfragmentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dontscare.CommonParameter;
import com.example.dontscare.R;
import com.gyf.immersionbar.ImmersionBar;

public class home_site extends AppCompatActivity {
    TextView home_now_site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.from_right, R.anim.no_slide);//设置滑动动画
        setContentView(R.layout.home_site);
        ImmersionBar.with(this)
                .barAlpha(0.3f)
                .init();

        ImageView title_return=(ImageView) findViewById(R.id.home_site_return);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        home_now_site=(TextView) findViewById(R.id.home_now_site);
        home_now_site.setText(CommonParameter.location);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_slide, R.anim.out_right);
    }
}
