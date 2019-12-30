package com.example.dontscare;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.gyf.immersionbar.ImmersionBar;
import com.next.easynavigation.view.EasyNavigationBar;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

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
//        getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
        ImmersionBar.with(this)
                .barAlpha(0.3f)
                .init();
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

