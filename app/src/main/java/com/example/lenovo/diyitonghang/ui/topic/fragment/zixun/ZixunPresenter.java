package com.example.lenovo.diyitonghang.ui.topic.fragment.zixun;


import com.example.lenovo.diyitonghang.base.BaseFragment;
import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.data.source.NewsDataSource;
import com.example.lenovo.diyitonghang.data.source.UserDataSource;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ZixunPresenter implements ZixunContract.Presenter {

    private ZixunContract.View mNewsView;

    private NewsDataSource mDataRepository;

    public ZixunPresenter(NewsDataSource dataRepository) {
        this.mDataRepository = dataRepository;
    }

    @Override
    public void getChannels() {
        //请求 库中 数据
        mDataRepository.loadChannels((BaseFragment) mNewsView, new Observer<List<Channel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Channel> channels) {
                mNewsView.onChannelsSuccess(channels);
            }

            @Override
            public void onError(Throwable e) {
                mNewsView.onChannelsFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void attachView(ZixunContract.View baseNewsView) {
        mNewsView = baseNewsView;
    }

    @Override
    public void dettachView() {
        mNewsView=null;
    }

}
