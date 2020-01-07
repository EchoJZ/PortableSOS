package com.example.dontscare.ui.person;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dontscare.R;
import com.gyf.immersionbar.ImmersionBar;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.from_right, R.anim.no_slide);//设置滑动动画
        setContentView(R.layout.home_help);
        ImmersionBar.with(this)
                .barAlpha(0.3f)
                .init();

        ImageView title_return=(ImageView) findViewById(R.id.home_help_return);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_slide, R.anim.out_right);
    }
}
