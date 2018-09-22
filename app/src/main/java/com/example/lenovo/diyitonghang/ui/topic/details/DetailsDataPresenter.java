package com.example.lenovo.diyitonghang.ui.topic.details;

import com.example.lenovo.diyitonghang.data.Comment;
import com.example.lenovo.diyitonghang.data.Data;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.NewsDetailsData;
import com.example.lenovo.diyitonghang.data.RelevantData;
import com.example.lenovo.diyitonghang.data.source.DetailsDataSource;
import com.example.lenovo.diyitonghang.data.source.NewsDataSource;
import com.example.lenovo.diyitonghang.ui.topic.fragment.reuseFragment.ReuseFragmentContract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsDataPresenter implements DetailsContract.DetailsPresenter {

    private DetailsContract.DetailsView mView;

    private DetailsDataSource mRemote;

    public DetailsDataPresenter(DetailsDataSource mRemote) {
        this.mRemote = mRemote;
    }

    @Override
    public void getDetails(String newsId) {
        mRemote.getNewsDetailsData(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DetailsData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DetailsData detailsData) {
                        mView.onDataDetailsSuccess(detailsData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onDataDetailsError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @Override
    public void getRelevantData(String newsId) {
        mRemote.getNewsRelevantData(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Data>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Data> data) {
                            mView.onDataRelevantData(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onDataDetailsError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getListComment(String userId) {
        mRemote.getListCommentData(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        mView.showListComment(comments);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onDataDetailsError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getCommentData(String text) {

        HashMap<String, String> map = new HashMap<>();
        map.put("userId","d56ea66e7ee741f498ca51242c8c6394");
        map.put("objectId","94231a0dc3da465297b2b5d3df15cc09");
        map.put("objectType","0");
        map.put("content",text);
        mRemote.getCommentData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        mView.showCommentData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onDataDetailsError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void attachView(DetailsContract.DetailsView view) {
        mView = view;


    }

    @Override
    public void dettachView() {
        mView=null;
    }


}
