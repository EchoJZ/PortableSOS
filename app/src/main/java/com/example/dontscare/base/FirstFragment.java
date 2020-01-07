package com.example.dontscare.base;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.dontscare.data.CommonParameter;
import com.example.dontscare.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstFragment extends Fragment implements View.OnClickListener{
    private Button get_site,update_site;
    private MediaPlayer mediaPlayer;
    private String file_url="";
    private View view;
    private String address="nothing";
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private Button btn_fanglang;
    private Button btn_weixie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_frist, null);
        init();

        btn_fanglang = view.findViewById(R.id.btn_fanglang);
        btn_weixie = view.findViewById(R.id.btn_weixie);

//        点击防狼声爆按钮
        btn_fanglang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    btn_fanglang.setBackgroundColor(Color.parseColor("#BEF44336"));
                    btn_fanglang.setText("播放中...");
                    Toast.makeText(getActivity(),"开始播放",Toast.LENGTH_SHORT).show();
                }
                else if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btn_fanglang.setBackgroundColor(Color.parseColor("#2196F3"));
                    btn_fanglang.setText("防狼声爆");
                    Toast.makeText(getActivity(),"播放暂停",Toast.LENGTH_SHORT).show();
                }
            }
        });
//        点击威胁声按钮
        btn_weixie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }


    /**
    ***初始化控件和媒体播放器
    **/
    private void init() {
        get_site=view.findViewById(R.id.get_site);
        update_site=view.findViewById(R.id.update_site);
        get_site.setOnClickListener(this);
        update_site.setOnClickListener(this);
        sendRequestWithOkhttp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_site:
                Toast.makeText(getActivity(), "您点击了获取位置", Toast.LENGTH_SHORT).show();
                getUserSite();
                break;
            case R.id.update_site:
                Toast.makeText(getActivity(), "您点击了上传位置！", Toast.LENGTH_SHORT).show();
                sendPostRequest();//上传位置信息
                break;

        }
    }


    /**
     ***获取录音
     **/
    private void sendRequestWithOkhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("file_type", "1").add("page", "1").add("size", "1").build();
                    Request request = new Request.Builder().url(CommonParameter.url_root+"/guard/list").
                            addHeader("receipt", CommonParameter.receipt).post(formBody).build();
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
                                try{
                                    jsonObject = new JSONObject(responseData);
                                    errCode = jsonObject.getInt("errCode");
                                    errMsg=jsonObject.getString("errMsg");
                                    if(errCode==0){
                                        JSONObject jsonObject_data = jsonObject.optJSONObject("data");
                                        JSONArray jsonArray=jsonObject_data.optJSONArray("list");
                                        JSONObject jsonObject1=jsonArray.getJSONObject(0);
                                        file_url = jsonObject1.getString("file_url");
                                        initMediaPlayer(file_url);
                                    }else if(errCode==1){
                                        Looper.prepare();
                                        Toast.makeText(getContext(),errMsg,Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }

                                } catch (JSONException e){
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


    /**
     ***初始化媒体播放器
     **/
    private void initMediaPlayer(String file_url) {
        Log.d("FirstFragment","执行initMediaPlayer");
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(file_url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Log.d("FirstFragment","完成加载网络音频");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     ***销毁媒体播放器
     **/
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }


    /**
    ***线程执行获取地理位置
    **/
    private void getUserSite() {
        final ExecutorService exec = Executors.newFixedThreadPool(1);
        Callable<String> call = new Callable<String>() {
            public String call() throws Exception {
                //百度定位
                mLocationClient = new LocationClient(getContext());
                //声明LocationClient类
                mLocationClient.registerLocationListener(myListener);
                //注册监听函数
                LocationClientOption option = new LocationClientOption();
                option.setOpenGps(true);
                option.setIsNeedAddress(true);
                mLocationClient.setLocOption(option);
                mLocationClient.start();
                return "线程执行完成.";
            }
        };

        try{
            Future<String> future = exec.submit(call);
            String obj = future.get(1000 * 1, TimeUnit.MILLISECONDS); //任务处理超时时间设为 1 秒
            System.out.println("任务成功返回:" + obj);
        } catch (TimeoutException ex) {
            System.out.println("处理超时啦....");
            ex.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     ***地理位置获取
     **/
    public class MyLocationListener extends BDAbstractLocationListener {
        String addr;

        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            Log.d("FirstFragment-MyLocLis","您当前的地址为"+addr);
            address=addr;
            CommonParameter.location=addr;
            Toast.makeText(getActivity(), "您当前的地址为："+address, Toast.LENGTH_SHORT).show();
        }
    }


    /**
    ***上传位置信息
    **/
    private void sendPostRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder()
                            .add("location", address)
                            .build();
                    Request request = new Request.Builder()
                            .url(CommonParameter.url_root+"/user/location_update")
                            .addHeader("receipt", CommonParameter.receipt)
                            .post(formBody)
                            .build();
                    Log.d("FirstFragment","开始更新位置信息");
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
                                        Toast.makeText(getActivity(),"成功更新地理位置！",Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                    else if(errCode==1){
                                        Looper.prepare();
                                        Toast.makeText(getActivity(),errMsg,Toast.LENGTH_SHORT).show();
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

