package com.example.lenovo.diyitonghang.ui.topic.fragment.zixun;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.base.BaseFragment;
import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.data.source.NewsDataRepository;
import com.example.lenovo.diyitonghang.data.source.UserDataRepository;
import com.example.lenovo.diyitonghang.data.source.local.NewsLocalDataSource;
import com.example.lenovo.diyitonghang.data.source.remote.NewsRemoteDataSource;
import com.example.lenovo.diyitonghang.ui.topic.activity.ControlFragmentNumActivity;
import com.example.lenovo.diyitonghang.ui.topic.adapter.MyAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZixunFragment extends BaseFragment implements ZixunContract.View {

    private ZixunContract.Presenter mZixunPresenter;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<String> strings = new ArrayList<>();
    private MyAdapter myAdapter;
   // private ArrayList<Fragment> fragments = new ArrayList<>();
    private String news="new";
    private RelativeLayout newsFragmentAddRl;
    private Toolbar mToolBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_zixun, container, false);

        ZixunPresenter zixunPresenter = new ZixunPresenter(NewsDataRepository.getInstance(NewsRemoteDataSource.getInstance(), NewsLocalDataSource.getInstance(getActivity())));
        setPresenter(zixunPresenter);
        initView(inflate);
        newsFragmentAddRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ControlFragmentNumActivity.class);
                startActivity(intent);
            }
        });
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolBar);
        return inflate;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_book_cover, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {

        }
        return true;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mZixunPresenter != null) {
            //获取频道
            mZixunPresenter.getChannels();
        }
    }

    @Override
    public void setPresenter(ZixunContract.Presenter presenter) {
        mZixunPresenter = presenter;
        mZixunPresenter.attachView(this);
    }

    private void initView(View inflate) {
        mToolBar = inflate.findViewById(R.id.toolbar);
        mTabLayout = (SlidingTabLayout) inflate.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) inflate.findViewById(R.id.viewPager);
        newsFragmentAddRl = (RelativeLayout) inflate.findViewById(R.id.news_fragment_add_rl);
    }

    @Override
    public void onChannelsSuccess(List<Channel> channels) {
        myAdapter = new MyAdapter(getChildFragmentManager(),channels);
        mViewPager.setAdapter(myAdapter);
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onChannelsFail(String msg) {
        Toast.makeText(getContext(), "加载频道列表失败", Toast.LENGTH_SHORT).show();
    }
}
