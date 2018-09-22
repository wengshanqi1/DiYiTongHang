package com.example.lenovo.diyitonghang.ui.topic.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.ui.topic.fragment.reuseFragment.ReuseFragment;

import java.util.List;

public class MyAdapter extends FragmentStatePagerAdapter {
    private List<Channel> newsChannelList1;
    private FragmentManager fm;
    public MyAdapter(FragmentManager fm,List<Channel> newsChannelList1) {
        super(fm);
        this.fm = fm;
        this.newsChannelList1 = newsChannelList1;
    }

    @Override
    public Fragment getItem(int position) {
        ReuseFragment reuseFragment = new ReuseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("channelId", newsChannelList1.get(position).getChannelId());
        bundle.putString("channelName", newsChannelList1.get(position).getChannelName());
        reuseFragment.setArguments(bundle);
        return reuseFragment;
    }



    @Override
    public int getCount() {
        return newsChannelList1.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return newsChannelList1.get(position).getChannelName();
    }
}
