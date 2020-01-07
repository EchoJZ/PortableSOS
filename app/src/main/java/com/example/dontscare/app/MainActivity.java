package com.example.dontscare.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.dontscare.base.FirstFragment;
import com.example.dontscare.R;
import com.example.dontscare.base.SecondFragment;
import com.example.dontscare.base.ThirdFragment;
import com.example.dontscare.data.CommonParameter;
import com.example.dontscare.utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;


public class MainActivity extends AppCompatActivity {

    private Context context;
    // 权限
    private static final int REQUEST_PERMISSION = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.SEND_SMS,
    };

    //未选中的Tab图片
    private int[] unSelectTabRes = new int[]{R.drawable.firstfragment1
            , R.drawable.secondfragment1, R.drawable.thirdfragment1};
    //选中的Tab图片
    private int[] selectTabRes = new int[]{R.drawable.firstfragment2
            , R.drawable.secondfragment2, R.drawable.thirdfragment2};
    //Tab标题
    private String[] title = new String[]{"", "", ""};
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TabLayout.Tab tabAtOne;
    private TabLayout.Tab tabAttwo;
    private TabLayout.Tab tabAtthree;
    private TabLayout.Tab tabAtfour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        initPermission();
        initView();
        initData();
        initListener();
        ImmersionBar.with(this)
                .barAlpha(0.3f)
                .init();
        Utils.getUserInfo(handler, CommonParameter.receipt);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Log.d("MainActivity.this","初始化用户信息："+msg.obj);
        }
    };

    /**
    ***初始化权限
    **/
    private void initPermission(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            boolean isGranted = true;
            for (String permission : PERMISSIONS) {
                int result = ActivityCompat.checkSelfPermission(this, permission);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false;
                    break;
                }
            }
            if (!isGranted) {
                // 还没有的话，去申请权限
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            boolean granted = true;
            for (int result : grantResults) {
                granted = result == PackageManager.PERMISSION_GRANTED;
                if (!granted) {
                    break;
                }
            }
            if (!granted) {
                // 没有赋予权限
            } else {
                // 已经赋予过权限了
            }
        }
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_content_view);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_view);

        //使用适配器将ViewPager与Fragment绑定在一起
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        //将TabLayout与ViewPager绑定
        tabLayout.setupWithViewPager(viewPager);

      /*  //设置方式一：
        //获取底部的单个Tab
        tabAtOne = tabLayout.getTabAt(0);
        tabAttwo = tabLayout.getTabAt(1);
        tabAtthree = tabLayout.getTabAt(2);
        tabAtfour = tabLayout.getTabAt(3);

        //设置Tab图片
        tabAtOne.setIcon(R.drawable.i8live_menu_home_press);
        tabAttwo.setIcon(R.drawable.i8live_menu_information_normal);
        tabAtthree.setIcon(R.drawable.i8live_menu_game_normal);
        tabAtfour.setIcon(R.drawable.i8live_menu_personl_normal);*/

        //设置方式二：
        for (int i = 0; i < title.length; i++) {
            if (i == 0) {
                tabLayout.getTabAt(0).setIcon(selectTabRes[0]);
            } else {
                tabLayout.getTabAt(i).setIcon(unSelectTabRes[i]);
            }
        }

    }

    private void initData() {

    }

    private void initListener() {
        //TabLayout切换时导航栏图片处理
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {//选中图片操作

                for (int i = 0; i < title.length; i++) {
                    if (tab == tabLayout.getTabAt(i)) {
                        tabLayout.getTabAt(i).setIcon(selectTabRes[i]);
                        viewPager.setCurrentItem(i);
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {//未选中图片操作

                for (int i = 0; i < title.length; i++) {
                    if (tab == tabLayout.getTabAt(i)) {
                        tabLayout.getTabAt(i).setIcon(unSelectTabRes[i]);
                    }
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //自定义适配器
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return new SecondFragment();//娱乐
            } else if (position == 2) {
                return new ThirdFragment();//游戏
            }
            return new FirstFragment();//首页
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}

