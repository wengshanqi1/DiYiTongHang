package com.example.lenovo.diyitonghang.data.source.remote;


import com.example.lenovo.diyitonghang.data.HttpResult;
import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.data.User;
import com.example.lenovo.diyitonghang.data.source.UserDataSource;
import com.example.lenovo.diyitonghang.data.source.remote.retrofit.DataRetrofit;
import com.example.lenovo.diyitonghang.data.source.remote.retrofit.FirstgaService;
import com.example.lenovo.diyitonghang.exception.ServerException;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;


import java.util.Map;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/*
 * created by taofu on 2018/8/29
 **/
public class UserRemoteDataSource implements UserDataSource {

    private FirstgaService service;


    public UserRemoteDataSource() {
        //仓库里边Service接口对象
        service = DataRetrofit.getRetrofitService();

    }
    //重写父类UserDataSource接口方法
    @Override
    public void login(RxFragment fragment, Map<String, String> params, Observer<User> observer) {

        //仓库里边Service接口对象 调用Service接口进行Post请求数据
        Observable<HttpResult<User>> observable = service.login(params);

        observable.flatMap(new Function<HttpResult<User>, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(HttpResult<User> userHttpResult) throws Exception {

                if (userHttpResult.getCode() == 0) {
                    return Observable.just(userHttpResult.getData());
                }
                return Observable.error(new ServerException(userHttpResult.getCode(),userHttpResult.getMessage()));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.<User>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(observer);
    }

    @Override
    public void sendVerificationCode(RxFragment fragment, Map<String, String> params, Observer<Object> observer) {
        Observable<HttpResult<Object>> observable = service.sendVerificationCode(params);

        observable.flatMap(new Function<HttpResult<Object>, ObservableSource<Object>>() {
            @Override
            public ObservableSource<Object> apply(HttpResult<Object> userHttpResult) throws Exception {

                if (userHttpResult.getCode() == 0) {
                    return Observable.just(new Object());
                }
                return Observable.error(new ServerException(userHttpResult.getCode(),userHttpResult.getMessage()));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.<Object>bindUntilEvent(FragmentEvent.DETACH))
                .subscribe(observer);

    }

}
