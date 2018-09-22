package com.example.lenovo.diyitonghang.data.source;

import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.NewsData;
import com.example.lenovo.diyitonghang.data.NewsDetailsData;
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
public class NewsDataRepository implements NewsDataSource {

    private static NewsDataRepository INSTANCE;

    private NewsDataSource mRemote;
    private NewsDataSource mLocal;


    private Map<String, NewsData> mMemoryCache;


    private NewsDataRepository(NewsDataSource remote, NewsDataSource local) {

        mRemote = remote;
        mLocal = local;


    }


    public static synchronized NewsDataRepository getInstance(NewsDataSource remote, NewsDataSource local) {
        if (INSTANCE == null) {
            INSTANCE = new NewsDataRepository(remote, local);
        }

        return INSTANCE;
    }

    @Override
    public void loadChannels(LifecycleProvider lifecycleProvider, Observer<List<Channel>> observer) {
        //请求库中频道
        mRemote.loadChannels(lifecycleProvider, observer);
    }

    @Override
    public void loadNewsByChannel(LifecycleProvider lifecycleProvider, Map<String, String> params, final Observer<NewsData> observer) {


        if (mMemoryCache != null && mMemoryCache.get(params.get(ReuseFragmentContract.PARAMS_CHANNER_ID)) != null) {
            Observable.just(mMemoryCache.get(params.get(ReuseFragmentContract.PARAMS_CHANNER_ID))).subscribe(observer);
            return;
        }


        mRemote.loadNewsByChannel(lifecycleProvider, params, observer);
    }

    @Override
    public Observable<NewsData> getNewsByChannel(Map<String, String> params) {

        final String channelId = params.get(ReuseFragmentContract.PARAMS_CHANNER_ID);
        if (mMemoryCache != null && mMemoryCache.get(params.get(ReuseFragmentContract.PARAMS_CHANNER_ID)) != null) {
            return Observable.just(mMemoryCache.get(params.get(ReuseFragmentContract.PARAMS_CHANNER_ID)));
        }
        Observable<NewsData> remoteObservable = getAndSaveRemoteNewsData(params);

        return Observable.concat(mLocal.getNewsByChannel(params), remoteObservable);
    }

    @Override
    public void saveNews(String channelId, NewsData data) {

    }

    @Override
    public Observable<DetailsData> getNewsDetailsData(String params) {
        Observable<DetailsData> newsDetailsData = mRemote.getNewsDetailsData(params);
        return newsDetailsData;
    }


    private Observable<NewsData> getAndSaveRemoteNewsData(Map<String, String> params) {
        final String channelId = params.get(ReuseFragmentContract.PARAMS_CHANNER_ID);

        Observable<NewsData> observable = mRemote.getNewsByChannel(params);

        return observable.doOnNext(new Consumer<NewsData>() {
            @Override
            public void accept(NewsData data) throws Exception {

                saveRefreshDataToMemory(channelId, data);
                mLocal.saveNews(channelId, mMemoryCache.get(channelId).getNewsDataForSdcardCache());
            }
        });
    }


    /**
     * 保存刷新回来的数据到内存
     *
     * @param channelId
     * @param data
     */
    private void saveRefreshDataToMemory(String channelId, NewsData data) {

        if (mMemoryCache == null) {
            mMemoryCache = new HashMap<>();
        }


        NewsData newsData = mMemoryCache.get(channelId);

        if (newsData == null) {
            newsData = new NewsData();
            mMemoryCache.put(channelId, data);
        }

        if (data.getNewList().size() >= 7) {
            newsData.replace(data);
        } else {
            newsData.mergeRefreshData(data);
        }
    }

    /**
     * 保存加载更多到内存
     *
     * @param channelId
     * @param data
     */
    private void saveLoadMoreDataToMemory(String channelId, NewsData data) {
        if (mMemoryCache == null) {
            mMemoryCache = new HashMap<>();
        }

        NewsData newsData = mMemoryCache.get(channelId);

        if (newsData == null) {
            newsData = new NewsData();
            mMemoryCache.put(channelId, data);
        }

        newsData.mergeLoadMoreData(data);


    }

}
