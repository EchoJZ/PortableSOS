package com.example.dontscare;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

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

public class SecondFragment extends Fragment {
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, null);
        button=view.findViewById(R.id.alarm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callToPhone();
            }
        });
        return view;
    }

    private void callToPhone() {
        /**
         *  借助ContextCompat.checkSelfPermission()方法判断是否授予权限，接收两个参数，Context和具体权限名，方法的返回值与
         *  PackageManager.PERMISSION_GRANTED做比较，相等说明已经授权
         */
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            /**
             * 同样借助ContextCompat.requestPermissions()方法弹出权限申请的对话框
             * 参数为Context,具体权限名，作为返回结果的识别码（自定义）
             */
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},1);
        }else{
            //已授权拨打电话
            try{
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+"110"));
                startActivity(intent);
            } catch (SecurityException e){
                e.printStackTrace();
            }
        }
    }

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

