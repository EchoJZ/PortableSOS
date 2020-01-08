package com.example.dontscare.base;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.dontscare.R;
import com.example.dontscare.data.CommonParameter;
import com.example.dontscare.data.MyData;
import com.example.dontscare.utils.Utils;

import static android.app.Activity.RESULT_OK;


public class SecondFragment extends Fragment implements View.OnClickListener{
    Button btn_dangerous;
    Button btn_safe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, null);

        btn_dangerous=view.findViewById(R.id.alarm_btn_dangerous);
        btn_safe=view.findViewById(R.id.alarm_btn_safe);
        btn_dangerous.setOnClickListener(this);
        btn_safe.setOnClickListener(this);

        return view;
    }


    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.alarm_btn_dangerous:
//                报警按钮的点击事件，拨打电话和向联系人发送求救短信
                Log.d("SecondFragment","点击了报警按钮");
                requestPermissions();
                break;
            case R.id.alarm_btn_safe:
                Log.d("SecondFragment","点击了我已安全按钮");
                if("1".equals(CommonParameter.security_status)) {
//                用户状态转为安全，修改安全状态，同时修改类CommonParameter的静态参数
                    Utils.updateSecurityStatus(handler, CommonParameter.security_status);
                }
                break;
        }
    }


    /**
     ***接受子线程返回的处理结果
     **/
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if("success".equals(msg.obj)){
                Log.d("SecondFragment","更新安全状态：成功\n");
                Toast.makeText(getActivity(),"更新安全状态：成功",Toast.LENGTH_SHORT).show();
            }else{
                Log.d("SecondFragment","更新安全状态：失败\n");
                Toast.makeText(getActivity(),"更新安全状态：失败",Toast.LENGTH_SHORT).show();
            }
        }
    };


    private void requestPermissions(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
            }
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);
            }
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
            callToPhone();
            sendMessage();
            if("0".equals(CommonParameter.security_status)){
                Log.d("SecondFragment","修改用户安全状态");
                //            修改用户当前的安全状态，同时修改类CommonParameter的静态参数
                Utils.updateSecurityStatus( handler , CommonParameter.security_status);
            }
        }
        else{
            Toast.makeText(getContext(),"获取权限失败",Toast.LENGTH_LONG);
            return;
        }
    }


    private void callToPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+ MyData.callNumber));
        startActivity(intent);
    }

    private void sendMessage(){
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(MyData.messageNumber, null, "【来自"
//                    +CommonParameter.user_name+"的呼救！】我遇到了危险，请到 "
//                    +CommonParameter.location+" 来救我！", null, null);
//            Toast.makeText(getContext(), "求救短信发送中",
//                    Toast.LENGTH_LONG).show();
//
//
//
//        } catch (Exception e) {
//            Toast.makeText(getContext(),
//                    "发送失败，请重新尝试.",
//                    Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
        Uri uri= Uri.parse("smsto:"+MyData.messageNumber);
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, uri);
        sms_intent.putExtra("sms_body", "【来自"
                    +CommonParameter.user_name+"的呼救！】我遇到了危险，请到 "
                    +CommonParameter.location+" 来救我！");
        startActivity(sms_intent);
    }

    /**
    ***注册BroadcastReceiver接收状态
    **/
//    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(ACTION_SEND_SMS)) {
//                if (getResultCode() == RESULT_OK) {
//                    Toast.makeText(SmsManageActivity.this, "Send SMS", Toast.LENGTH_LONG).show();
//                }
//            } else if (intent.getAction().equals(ACTION_DELIVERY_SMS)) {
//                if (getResultCode() == RESULT_OK) {
//                    Toast.makeText(SmsManageActivity.this, "Delivery SMS", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//
//    };


    /**
     * @param requestCode 前面定义的反馈结果识别码
     * @param permissions
     * @param grantResults 授权结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                //授权重新调用callToPhone
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    callToPhone();
                } else {
                    //不授权提示用户的操作
                    Toast.makeText(getContext(), "您拒绝授权", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }






}

