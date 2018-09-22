package com.example.lenovo.diyitonghang.ui.topic.fragment.reuseFragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.base.BaseFragment;
import com.example.lenovo.diyitonghang.data.NewsData;
import com.example.lenovo.diyitonghang.data.source.NewsDataRepository;
import com.example.lenovo.diyitonghang.data.source.local.NewsLocalDataSource;
import com.example.lenovo.diyitonghang.data.source.remote.NewsRemoteDataSource;
import com.example.lenovo.diyitonghang.ui.topic.activity.DataDetailsActivity;
import com.example.lenovo.diyitonghang.ui.topic.adapter.TopicMyAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReuseFragment extends BaseFragment implements ReuseFragmentContract.View {

    private int scorllY;
    private int position;
    private RecyclerView mRecyclerTopic;
    private TopicMyAdapter topicMyAdapter;
    private String mChannelName;
    private ReuseFragmentContract.Presenter mPresenter;
    private RefreshLayout refreshLayout;
    private String id;
    private int tag=1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            position = savedInstanceState.getInt("position",0);
            scorllY = savedInstanceState.getInt("scrollY",0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_default, container, false);


        initView(inflate);

        ReuseFragmentPreseter reuseFragmentPreseter = new ReuseFragmentPreseter(NewsDataRepository.getInstance(NewsRemoteDataSource.getInstance(),NewsLocalDataSource.getInstance(getContext())));
        setPresenter(reuseFragmentPreseter);
        Bundle arguments = getArguments();
        id = arguments.getString("channelId");
        mChannelName = arguments.getString("channelName");
        mPresenter.getNewsByChannel(id);

        /*mRecyclerTopic.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int fistVP = linearLayoutManager.findFirstVisibleItemPosition();
                    View v = linearLayoutManager.findViewByPosition(fistVP);
                    position = fistVP;
                    scorllY = v.getTop();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000*//*,false*//*);//传入false表示刷新失败
                mPresenter.getResTopic(id,0);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000*//*,false*//*);//传入false表示加载失败
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.getResTopic(id, tag);
                    }
                },3000);
            }
        });*/
        return inflate;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
        outState.putInt("scrollY", scorllY);
    }


    @Override
    public void setPresenter(ReuseFragmentContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);

    }

    private void initView(View view) {
        mRecyclerTopic = (RecyclerView) view.findViewById(R.id.recycler_topic);
        mRecyclerTopic.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        //设置 Header 为 贝塞尔雷达 样式
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
    }
    private void scrollToTargetPosition(){
        if(position != 0 && scorllY != 0){
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerTopic.getLayoutManager();
            linearLayoutManager.scrollToPositionWithOffset(position, scorllY);
        }
    }

    @Override
    public void onNewsListSuccess(final NewsData news) {
        TopicMyAdapter topicMyAdapter = new TopicMyAdapter(news.getNewList());
        mRecyclerTopic.setAdapter(topicMyAdapter);
        topicMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), DataDetailsActivity.class);
                intent.putExtra("news",news.getNewList().get(position).getNewsId());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onNewsListFail(String msg) {

    }
}
