package com.example.lenovo.diyitonghang.ui.topic.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.base.BaseActivity;
import com.example.lenovo.diyitonghang.ui.topic.fragment.information.InformationFragment;
import com.example.lenovo.diyitonghang.ui.topic.fragment.me.MeFragment;
import com.example.lenovo.diyitonghang.ui.topic.fragment.zixun.ZixunFragment;
import com.example.lenovo.diyitonghang.utils.StatusBarManager;
import com.hjm.bottomtabbar.BottomTabBar;

public class TopicActivity extends BaseActivity{
    private BottomTabBar mBottomTabBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic);
//        initView();
        StatusBarManager.immersive(this);
        mBottomTabBar = (BottomTabBar) findViewById(R.id.bottom_tab_bar);
        //这个init ( getSupportFragmentManager() )方法一定要第一个调用，没有//这个初始化，后边什么也做不了。
        mBottomTabBar.init(getSupportFragmentManager())
                .addTabItem("资讯",R.drawable.zixun2 , R.drawable.new_high2x, ZixunFragment.class)
                .addTabItem("话题",  R.drawable.xiaoxi2,R.drawable.huati2, InformationFragment.class)
                .addTabItem("我的",  R.drawable.new_one_di2,R.drawable.wode2, MeFragment.class);
    }
}
