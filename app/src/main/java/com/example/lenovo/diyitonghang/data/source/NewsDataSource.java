package com.example.lenovo.diyitonghang.data.source;

import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.NewsData;
import com.example.lenovo.diyitonghang.data.NewsDetailsData;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;

/*
 * created by taofu on 2018/9/5
 **/
public interface NewsDataSource {

    void loadChannels(LifecycleProvider lifecycleProvider, Observer<List<Channel>> observer);

    void loadNewsByChannel(LifecycleProvider lifecycleProvider, Map<String,String> params,Observer<NewsData> observer);



    Observable<NewsData> getNewsByChannel(Map<String,String> params);


    void saveNews(String channelId, NewsData data);


    Observable<DetailsData> getNewsDetailsData(String params);

}
