package com.example.dontscare.ui.person;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dontscare.data.CommonParameter;
import com.example.dontscare.R;
import com.example.dontscare.utils.Utils;
import com.gyf.immersionbar.ImmersionBar;

public class DetailsActivity extends AppCompatActivity {

    EditText home_details_email,home_details_user_name2,home_details_user_intro,home_details_user_password;
    TextView home_details_user_name1,home_details_location,home_details_update_time,home_details_security_status;
    Button home_details_edit;

    String name;
    String password;
    String introduce;

//    线程变量
    MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_details);

        //设置滑动动画
        overridePendingTransition(R.anim.from_right, R.anim.no_slide);

        //设置状态栏
        ImmersionBar.with(this)
                .barAlpha(0.3f)
                .init();

        //设置返回按钮事件
        ImageView title_return=(ImageView) findViewById(R.id.home_details_return);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //初始化控件并显示出来
        init();
        setShowData();

        //异步加载ui
        myTask = new MyTask();

//        保存按钮的点击事件，能修改的个人信息为：用户名name，密码password，个人介绍intro
        home_details_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=home_details_user_name2.getText().toString();
                password=home_details_user_password.getText().toString();
                introduce=home_details_user_intro.getText().toString();
                Utils.modifyUserInfo(handler, name, password, introduce);
            }
        });
    }

    Handler handler = new Handler(){
      @Override
      public void handleMessage(Message msg){
          if(msg.obj == "success"){
              Toast.makeText(DetailsActivity.this,"修改成功！",Toast.LENGTH_SHORT).show();
              Log.d("DetailsActivity","修改后："+name+" "+password+" "+introduce+"\n");
              CommonParameter.updateUserInfo(name, password, introduce);
//              myTask.execute();
          }else if(msg.obj == "fail"){
              Toast.makeText(DetailsActivity.this,"修改失败！",Toast.LENGTH_SHORT).show();
          }
      }
    };

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_slide, R.anim.out_right);
    }

    private void init() {
        home_details_user_name1 = (TextView) findViewById(R.id.home_details_user_name1);
        home_details_user_name2 = (EditText) findViewById(R.id.home_details_user_name2);
        home_details_email = (EditText) findViewById(R.id.home_details_email);
        home_details_user_intro = (EditText) findViewById(R.id.home_details_user_intro);
        home_details_user_password = (EditText) findViewById(R.id.home_details_user_password);
        home_details_location = (TextView) findViewById(R.id.home_details_location);
        home_details_update_time = (TextView) findViewById(R.id.home_details_update_time);
        home_details_security_status = (TextView) findViewById(R.id.home_details_security_status);
        home_details_edit = (Button) findViewById(R.id.home_details_edit);
        home_details_email.setFocusable(false);
    }

    /**
    ***定义用户信息至控件中
    **/
    private void setShowData(){
        home_details_user_name1.setText(CommonParameter.user_name);
        home_details_user_name2.setText(CommonParameter.user_name);
        home_details_email.setText(CommonParameter.email);
        home_details_user_intro.setText(CommonParameter.user_intro);
        home_details_user_password.setText(CommonParameter.password);
        home_details_location.setText(CommonParameter.location);
        home_details_update_time.setText(CommonParameter.update_time);
        if(CommonParameter.security_status.equals("0")){
            home_details_security_status.setText("安全");
        }else{
            home_details_security_status.setText("不安全");
        }
    }

    private class MyTask extends AsyncTask<String, Integer, String> {

//        执行任务中的耗时操作，返回线程执行结果
        @Override
        protected String doInBackground(String... params) {
            try {
                setShowData();
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

//        接收线程任务执行结果、将执行结果显示到UI组件
        @Override
        protected void onPostExecute(String result) {

        }
    }


}
