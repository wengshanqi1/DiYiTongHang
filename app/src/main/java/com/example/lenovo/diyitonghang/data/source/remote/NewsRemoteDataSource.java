package com.example.lenovo.diyitonghang.data.source.remote;

import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.data.ChannelData;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.HttpResult;
import com.example.lenovo.diyitonghang.data.NewsData;
import com.example.lenovo.diyitonghang.data.NewsDetailsData;
import com.example.lenovo.diyitonghang.data.source.NewsDataSource;
import com.example.lenovo.diyitonghang.data.source.remote.retrofit.DataRetrofit;
import com.example.lenovo.diyitonghang.data.source.remote.retrofit.FirstgaService;
import com.example.lenovo.diyitonghang.exception.ServerException;
import com.example.lenovo.diyitonghang.utils.Logger;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/*
 * created by taofu on 2018/9/13
 **/
public class NewsRemoteDataSource implements NewsDataSource {

    private FirstgaService service;

    private static NewsRemoteDataSource INSTANCE;

    private NewsRemoteDataSource() {
        service = DataRetrofit.getRetrofitService();

    }


    public static synchronized NewsRemoteDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new NewsRemoteDataSource();
        }
        return INSTANCE;
    }
    //解析库中频道
    @Override
    public void loadChannels(LifecycleProvider lifecycleProvider, Observer<List<Channel>> observer) {
        Observable<HttpResult<ChannelData>> observable = service.loadChannels();

        buildObserver(observable.flatMap(new Function<HttpResult<ChannelData>, ObservableSource<List<Channel>>>() {
            @Override
            public ObservableSource<List<Channel>> apply(HttpResult<ChannelData> channelDataHttpResult) throws Exception {
                if(channelDataHttpResult.getCode() == 0 ){
                    if(channelDataHttpResult.getData() != null && channelDataHttpResult.getData().getNewsChannelList() != null){
                        return Observable.just(channelDataHttpResult.getData().getNewsChannelList());
                    }else{
                        return Observable.error(new ServerException(1001,"服务器内部异常"));
                    }

                }
                return Observable.error(new ServerException(channelDataHttpResult.getCode(),channelDataHttpResult.getMessage()));
            }
        }), observer,lifecycleProvider);
    }

    @Override
    public void loadNewsByChannel(LifecycleProvider lifecycleProvider, Map<String, String> params, Observer<NewsData> observer) {

    }

    @Override
    public Observable<NewsData> getNewsByChannel(Map<String, String> params) {
        Observable<HttpResult<NewsData>> observable = service.fetchNewNews(params);

      return observable.flatMap(new Function<HttpResult<NewsData>, ObservableSource<NewsData>>() {
            @Override
            public ObservableSource<NewsData> apply(HttpResult<NewsData> newsDataHttpResult) throws Exception {

                if(newsDataHttpResult.getCode() == 0){
                    NewsData newsData = newsDataHttpResult.getData();
                    if(newsData != null && newsData.getNewList()!= null && newsData.getNewList().size() >0){
                        return Observable.just(newsData);
                    }

                    return Observable.error(new ServerException(1001,"服务器内部异常"));
                }

                return Observable.error(new ServerException(newsDataHttpResult.getCode(),newsDataHttpResult.getMessage()));
            }
        });

    }
    @Override
    public void saveNews(String channelId, NewsData data) {

    }
    @Override
    public Observable<DetailsData> getNewsDetailsData(String params) {

        Observable<HttpResult<DetailsData>> observable = service.newsDetails(params,"d56ea66e7ee741f498ca51242c8c6394");
        return observable.flatMap(new Function<HttpResult<DetailsData>, ObservableSource<DetailsData>>() {
            @Override
            public ObservableSource<DetailsData> apply(HttpResult<DetailsData> newsDetailsDataHttpResult) throws Exception {
                if(newsDetailsDataHttpResult.getCode() == 0){
                    DetailsData data = newsDetailsDataHttpResult.getData();
                    if(data != null){
                        return Observable.just(data);
                    }
                    return Observable.error(new ServerException(1001,"服务器内部异常"));
                }

                return Observable.error(new ServerException(newsDetailsDataHttpResult.getCode(),newsDetailsDataHttpResult.getMessage()));
            }
        });
    }






    private <T> void buildObserver(Observable<T> observable,Observer<T> observer,LifecycleProvider lifecycleProvider){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose((lifecycleProvider instanceof RxAppCompatActivity) ?(((RxAppCompatActivity)lifecycleProvider).<T>bindUntilEvent(ActivityEvent.DESTROY)) :  (((RxFragment)lifecycleProvider).<T>bindUntilEvent(FragmentEvent.DETACH)))
                .subscribe(observer);
    }
}