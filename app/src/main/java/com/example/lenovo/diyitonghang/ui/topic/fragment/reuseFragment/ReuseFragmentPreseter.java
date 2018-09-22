package com.example.lenovo.diyitonghang.ui.topic.fragment.reuseFragment;

import com.example.lenovo.diyitonghang.base.MyObserver;
import com.example.lenovo.diyitonghang.data.NewsData;
import com.example.lenovo.diyitonghang.data.source.NewsDataSource;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReuseFragmentPreseter implements ReuseFragmentContract.Presenter {

    private NewsDataSource mNewsDataRepository;

    private ReuseFragmentContract.View mView;

    public ReuseFragmentPreseter(NewsDataSource mNewsDataRepository) {
        this.mNewsDataRepository = mNewsDataRepository;
    }
    @Override
    public void attachView(ReuseFragmentContract.View view) {
        mView = view;
    }

    @Override
    public void dettachView() {
        mView=null;
    }

    @Override
    public void getNewsByChannel(String channelId) {
        Map<String, String> parmas = new HashMap<>();
        parmas.put("userId", "d56ea66e7ee741f498ca51242c8c6394");
        parmas.put("channelId", channelId);
        parmas.put("cursor", "0");
        mNewsDataRepository.getNewsByChannel(parmas)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose((mView instanceof RxAppCompatActivity) ?(((RxAppCompatActivity)mView).<NewsData>bindUntilEvent(ActivityEvent.DESTROY)) :  (((RxFragment)mView).<NewsData>bindUntilEvent(FragmentEvent.DETACH)))
                .subscribe(new MyObserver<NewsData>(){
                    @Override
                    public void onNext(NewsData data) {
                        mView.onNewsListSuccess(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onNewsListFail(e.getMessage());
                    }
                });

    }
}
