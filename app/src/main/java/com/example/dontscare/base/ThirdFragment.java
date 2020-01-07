package com.example.dontscare.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.dontscare.data.CommonParameter;
import com.example.dontscare.R;
import com.example.dontscare.bean.ContactBean;
import com.example.dontscare.ui.person.AboutActivity;
import com.example.dontscare.ui.person.ContactActivity;
import com.example.dontscare.ui.person.DetailsActivity;
import com.example.dontscare.ui.person.HelpActivity;
import com.example.dontscare.ui.person.SettingActivity;
import com.example.dontscare.ui.person.SiteActivity;
import org.json.JSONArray;
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


public class ThirdFragment extends Fragment {
    TextView user_name;
    TextView user_val;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_third, container,false);
        init();

        //设置home页面的所有点击事件
        TextView tv1=(TextView) view.findViewById(R.id.home_details);
        TextView tv2=(TextView) view.findViewById(R.id.home_contact);
        TextView tv3=(TextView) view.findViewById(R.id.home_site);
        TextView tv4=(TextView) view.findViewById(R.id.home_help);
        TextView tv5=(TextView) view.findViewById(R.id.home_about);
        ImageView img1=(ImageView) view.findViewById(R.id.home_setting);
        String text1="我的资料";
        String text2="我的紧急联系人";
        String text3="我的位置";
        String text4="帮助";
        String text5="关于我们";
        SpannableString ss1=new SpannableString(text1);
        SpannableString ss2=new SpannableString(text2);
        SpannableString ss3=new SpannableString(text3);
        SpannableString ss4=new SpannableString(text4);
        SpannableString ss5=new SpannableString(text5);

        //点击获取用户资料
        ss1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                getUserDetails();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 0, text1.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv1.setText(ss1);
        tv1.setMovementMethod(LinkMovementMethod.getInstance());

        //点击紧急联系人
        ss2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                getUserContact();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 0, text2.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv2.setText(ss2);
        tv2.setMovementMethod(LinkMovementMethod.getInstance());

        //点击当前位置
        ss3.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i=new Intent(getContext(), SiteActivity.class);
                startActivity(i);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 0, text3.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv3.setText(ss3);
        tv3.setMovementMethod(LinkMovementMethod.getInstance());

        //点击帮助
        ss4.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i=new Intent(getContext(), HelpActivity.class);
                startActivity(i);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 0, text4.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv4.setText(ss4);
        tv4.setMovementMethod(LinkMovementMethod.getInstance());

        //点击关于我们
        ss5.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent i=new Intent(getContext(), AboutActivity.class);
                startActivity(i);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 0, text5.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tv5.setText(ss5);
        tv5.setMovementMethod(LinkMovementMethod.getInstance());

        //点击右上角设置
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    /**
    ***初始化界面信息（姓名，邮箱）
    **/
    private void init(){
        TextView userName=(TextView) view.findViewById(R.id.frgm_user_name);
        TextView userEmail=(TextView) view.findViewById(R.id.frgm_user_email);
        String sName = CommonParameter.user_name;
        String sEmail = CommonParameter.email;
        userName.setText(sName);
        userEmail.setText(sEmail);

    }


    /**
     * 点击用户资料按钮
     */
    private void getUserDetails()  {
        Intent i=new Intent(getContext(), DetailsActivity.class);
        startActivity(i);
    }


    /**
     ***点击紧急联系人列表
     **/
    private void getUserContact(){
        //发起请求
        RequestBody formBody = new FormBody.Builder()
                .add("page","1")
                .add("size","5")
                .build();
        final Request request = new Request.Builder()
                .url(CommonParameter.url_root+"/relatives/list")
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
                                String reponseData=response.body().string();
                                System.out.println("点击我的紧急联系人——返回结果："+reponseData);
                                JSONObject jsonObject= null;
                                try {
                                    jsonObject = new JSONObject(reponseData);
                                    errCode = jsonObject.getInt("errCode");
                                    errMsg=jsonObject.getString("errMsg");
                                    if(errCode==0){
                                        JSONObject jsonObject_data = jsonObject.optJSONObject("data");
                                        JSONArray jsonArray=jsonObject_data.optJSONArray("list");
//                                        System.out.println("点击我的紧急联系人——返回jsonArray："+jsonArray);
                                        CommonParameter.clearUserList();
                                        for(int i=0;i<jsonArray.length();i++){
                                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                            String name=jsonObject1.getString("user_name");
                                            String location=jsonObject1.getString("location");
                                            String status=jsonObject1.getString("security_status");
                                            if(status.equals("0")){
                                                status="安全";
                                            }else {
                                                status="不安全";
                                            }
                                            ContactBean contactBean =new ContactBean(name,R.drawable.head7+i,location,status);
                                            CommonParameter.list.add(contactBean);
                                        }
                                        Intent i=new Intent(getContext(), ContactActivity.class);
                                        startActivity(i);
                                    }else if(errCode==1){
                                        Looper.prepare();
                                        Toast.makeText(getContext(),errMsg,Toast.LENGTH_SHORT).show();
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
}
