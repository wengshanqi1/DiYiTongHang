package com.example.lenovo.diyitonghang.data.source.remote;

import com.example.lenovo.diyitonghang.AppConstact;
import com.example.lenovo.diyitonghang.data.Comment;
import com.example.lenovo.diyitonghang.data.Data;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.HttpResult;
import com.example.lenovo.diyitonghang.data.ListComment;
import com.example.lenovo.diyitonghang.data.RelevantData;
import com.example.lenovo.diyitonghang.data.source.DetailsDataSource;
import com.example.lenovo.diyitonghang.data.source.remote.retrofit.DataRetrofit;
import com.example.lenovo.diyitonghang.data.source.remote.retrofit.FirstgaService;
import com.example.lenovo.diyitonghang.exception.ServerException;
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
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * created by taofu on 2018/9/13
 **/
public class DetailsRemoteDataSource implements DetailsDataSource {

    private FirstgaService service;

    private static DetailsRemoteDataSource INSTANCE;

    private DetailsRemoteDataSource() {
        service = DataRetrofit.getRetrofitService();

    }


    public static synchronized DetailsRemoteDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new DetailsRemoteDataSource();
        }
        return INSTANCE;
    }
    @Override
    public Observable<DetailsData> getNewsDetailsData(String params) {

        Observable<HttpResult<DetailsData>> observable = service.newsDetails(params, "d56ea66e7ee741f498ca51242c8c6394");
        return observable.flatMap(new Function<HttpResult<DetailsData>, ObservableSource<DetailsData>>() {
            @Override
            public ObservableSource<DetailsData> apply(HttpResult<DetailsData> newsDetailsDataHttpResult) throws Exception {
                if (newsDetailsDataHttpResult.getCode() == 0) {
                    DetailsData data = newsDetailsDataHttpResult.getData();
                    if (data != null) {
                        return Observable.just(data);
                    }
                    return Observable.error(new ServerException(1001, "服务器内部异常"));
                }

                return Observable.error(new ServerException(newsDetailsDataHttpResult.getCode(), newsDetailsDataHttpResult.getMessage()));
            }
        });
    }

    @Override
    public Observable<List<Data>> getNewsRelevantData(String params) {
        Observable<HttpResult<List<Data>>> observable = service.getRelevant(params);

        return observable.flatMap(new Function<HttpResult<List<Data>>, ObservableSource<List<Data>>>() {
            @Override
            public ObservableSource<List<Data>> apply(HttpResult<List<Data>> listHttpResult) throws Exception {
                if (listHttpResult != null) {
                    return Observable.just(listHttpResult.getData());
                }
                return Observable.error(new ServerException(1001, "服务器内部异常"));
            }
        });
    }

    @Override
    public Observable<List<Comment>> getListCommentData(String params) {
        Observable<HttpResult<ListComment>> observable = service.getListComment("d56ea66e7ee741f498ca51242c8c6394");

        return observable.flatMap(new Function<HttpResult<ListComment>, ObservableSource<List<Comment>>>() {
            @Override
            public ObservableSource<List<Comment>> apply(HttpResult<ListComment> listCommentHttpResult) throws Exception {
                if (listCommentHttpResult != null) {
                    return Observable.just(listCommentHttpResult.getData().getUserCommentList());
                }
                return Observable.error(new ServerException(1001, "服务器内部异常"));
            }
        });
    }

    @Override
    public Observable<Object> getCommentData(Map<String, String> map) {
        Observable<HttpResult<Object>> observable = service.getCommentData(map);

        return observable.flatMap(new Function<HttpResult<Object>, ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> apply(HttpResult<Object> userHttpResult) throws Exception {
                if (userHttpResult.getCode() == 0) {
                    return Observable.just(new Object());
                }
                return Observable.error(new ServerException(userHttpResult.getCode(),userHttpResult.getMessage()));
            }
        });
    }
}