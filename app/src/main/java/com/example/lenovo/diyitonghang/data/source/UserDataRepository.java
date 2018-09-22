package com.example.lenovo.diyitonghang.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.data.User;
import com.example.lenovo.diyitonghang.data.source.remote.UserRemoteDataSource;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.trello.rxlifecycle2.internal.Preconditions;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/*
 * created by taofu on 2018/8/29
 **/
public class UserDataRepository implements UserDataSource {


    @NonNull
    private UserDataSource mRemoteDataSource;

    @Nullable
    private static UserDataRepository INSTANCE = null;

    //通过构造得到M层对象
    private UserDataRepository(@NonNull UserDataSource remoteDataSource){
        mRemoteDataSource = Preconditions.checkNotNull(remoteDataSource, "Login remote data source is not allowed null");
    }


    public static UserDataRepository getInstance(){

        if(INSTANCE == null){
            synchronized (UserDataRepository.class){
                if(INSTANCE == null){
                    UserDataSource userRemoteDataSource = new UserRemoteDataSource();
                    INSTANCE = new UserDataRepository(userRemoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    //实现父类UserDataSource接口方法
    @Override
    public void login(RxFragment fragment, Map<String, String> params, Observer<User> observer) {
        //通过构造得到M层对象调用自己方法请求数据
        mRemoteDataSource.login(fragment, params,observer);
    }

    @Override
    public void sendVerificationCode(RxFragment fragment, Map<String, String> params, Observer<Object> observer) {
        mRemoteDataSource.sendVerificationCode(fragment, params, observer);
    }

}
