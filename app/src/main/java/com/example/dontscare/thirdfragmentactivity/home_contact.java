package com.example.dontscare.thirdfragmentactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dontscare.CommonParameter;
import com.example.dontscare.ContactAdapter;
import com.example.dontscare.MainActivity;
import com.example.dontscare.R;
import com.example.dontscare.UserContact;
import com.example.dontscare.begin.begin_login;
import com.example.dontscare.begin.begin_welcome;
import com.gyf.immersionbar.ImmersionBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class home_contact extends AppCompatActivity {
    private List<UserContact> userList=new ArrayList<>();
    Button add;
    RecyclerView recyclerView;
    private static final int CHANGE_TEXT=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.from_right, R.anim.no_slide);//设置滑动动画
        setContentView(R.layout.home_contact);
        ImmersionBar.with(this)
                .barAlpha(0.3f)
                .init();
        ImageView title_return=(ImageView) findViewById(R.id.home_contact_return);
        title_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //联系人列表
        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        initContactUser();
        ContactAdapter adapter=new ContactAdapter(userList);
        recyclerView.setAdapter(adapter);

        //添加好友
        add=(Button) findViewById(R.id.home_contact_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"你点击了添加好友按钮",Toast.LENGTH_SHORT).show();
                addDialog();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_slide, R.anim.out_right);
    }

    /**
    ***初始化好友对象List
    **/
    private void initContactUser(){
        int size=CommonParameter.list.size();
        Log.d("home_contact", String.valueOf(size));
        for(int i=0;i<size;i++){
            UserContact user1=CommonParameter.list.get(i);
            userList.add(user1);
        }
    }

    /**
    ***初始化适配器并执行
    **/
    private void initAdapter(){
        ContactAdapter adapter=new ContactAdapter(userList);
        recyclerView.setAdapter(adapter);
    }


    /*
     **添加好友弹框
     */
    private void addDialog(){
        final EditText et = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加好友");
        builder.setMessage("请输入手机号");
        builder.setView(et);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getApplicationContext(), et.getText().toString(),Toast.LENGTH_LONG).show();
                String addNumber="";
                addNumber = et.getText().toString();
                addRequest(addNumber);
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
     * 接收解析后传过来的数据
     */
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CHANGE_TEXT:
                    //在这里可以进行UI操作
                    Log.d("home_contact","执行了Handler");
                    CommonParameter.list.clear();

                    break;
                default:
                    break;
            }
        }
    };


    /**
    ***添加好友的post请求
    **/
    private void addRequest(String addNumber)  {
        //建立请求表单，添加上传服务器的参数
        RequestBody formBody = new FormBody.Builder()
                .add("email",addNumber)
                .build();

        //发起请求
        final Request request = new Request.Builder()
                .url(CommonParameter.url_root+"/relatives/add")
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
                                    jsonObject = new JSONObject(responseData);
                                    errCode = jsonObject.getInt("errCode");
                                    errMsg=jsonObject.getString("errMsg");
                                    if(errCode==0){
                                        Looper.prepare();
                                        Toast.makeText(getBaseContext(),"添加成功！",Toast.LENGTH_SHORT).show();
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
                //?为何在外面执行才有效
                Message message=new Message();
                message.what=CHANGE_TEXT;
                handler.sendMessage(message);
            }
        }).start();
    }



}
