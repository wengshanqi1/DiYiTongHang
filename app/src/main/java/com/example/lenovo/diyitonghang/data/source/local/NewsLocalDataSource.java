package com.example.lenovo.diyitonghang.data.source.local;

import android.content.Context;

import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.NewsData;
import com.example.lenovo.diyitonghang.data.NewsDetailsData;
import com.example.lenovo.diyitonghang.data.source.NewsDataSource;
import com.example.lenovo.diyitonghang.ui.topic.fragment.reuseFragment.ReuseFragmentContract;
import com.example.lenovo.diyitonghang.utils.FilesUtils;
import com.example.lenovo.diyitonghang.utils.SystemFacade;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

/*
 * created by taofu on 2018/9/13
 **/
public class NewsLocalDataSource implements NewsDataSource {


    private static NewsLocalDataSource INSTANCE;

    private File mDataCacheFileDir;
    private Context context;

    private NewsLocalDataSource(Context context) {
        File cacheDir = SystemFacade.getExternalCacheDir(context);
        if (cacheDir != null && cacheDir.exists()) {
            mDataCacheFileDir = new File(cacheDir, "news_files");
        }
    }


    public static synchronized NewsLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NewsLocalDataSource(context);
        }

        return INSTANCE;
    }
    @Override
    public void loadChannels(LifecycleProvider lifecycleProvider, Observer<List<Channel>> observer) {

    }

    @Override
    public void loadNewsByChannel(LifecycleProvider lifecycleProvider, Map<String, String> params, Observer<NewsData> observer) {

    }

    @Override
    public Observable<NewsData> getNewsByChannel(Map<String, String> params) {
        final String channelId = params.get(ReuseFragmentContract.PARAMS_CHANNER_ID);
       return Observable.create(new ObservableOnSubscribe<NewsData>() {
            @Override
            public void subscribe(ObservableEmitter<NewsData> emitter) throws Exception {

                NewsData newsData = FilesUtils.getNewsFromFile(createCacheFile(channelId));

                if(newsData != null){
                    emitter.onNext(newsData);
                }
                emitter.onComplete();

            }
        });



    }

    @Override
    public void saveNews(String channelId, NewsData data) {
        FilesUtils.writeNewsDataToFile(data, createCacheFile(channelId));
    }

    @Override
    public Observable<DetailsData> getNewsDetailsData(String params) {
        return null;
    }


    private File createCacheFile(String channelId) {
        if (mDataCacheFileDir != null) {
            if(!mDataCacheFileDir.exists()){
                mDataCacheFileDir.mkdirs();
            }
            return new File(mDataCacheFileDir, channelId);
        }

        return null;
    }
}
