package com.example.lenovo.diyitonghang.data.source;

import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.data.Comment;
import com.example.lenovo.diyitonghang.data.Data;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.NewsData;
import com.example.lenovo.diyitonghang.data.RelevantData;
import com.example.lenovo.diyitonghang.ui.topic.fragment.reuseFragment.ReuseFragmentContract;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

/*
 * created by taofu on 2018/9/5
 **/
public class DetailsDataRepository implements DetailsDataSource {

    private static DetailsDataRepository INSTANCE;

    private DetailsDataSource mRemote;

    private DetailsDataRepository(DetailsDataSource remote) {
        mRemote = remote;
    }


    public static synchronized DetailsDataRepository getInstance(DetailsDataSource remote) {
        if (INSTANCE == null) {
            INSTANCE = new DetailsDataRepository(remote);
        }
        return INSTANCE;
    }

    @Override
    public Observable<DetailsData> getNewsDetailsData(String params) {
        return mRemote.getNewsDetailsData(params);
    }

    @Override
    public Observable<List<Data>> getNewsRelevantData(String params) {
        return mRemote.getNewsRelevantData(params);
    }

    @Override
    public Observable<List<Comment>> getListCommentData(String params) {
        return mRemote.getListCommentData(params);
    }

    @Override
    public Observable<Object> getCommentData(Map<String, String> map) {
        return mRemote.getCommentData(map);
    }

}
