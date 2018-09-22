package com.example.lenovo.diyitonghang.ui.login;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.data.User;
import com.example.lenovo.diyitonghang.data.source.UserDataSource;
import com.example.lenovo.diyitonghang.ui.acticity.MainActivity;
import com.example.lenovo.diyitonghang.utils.Logger;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginPresenter implements LoginContract.Presenter{

    public LoginContract.View mView;

    //
    private UserDataSource mUserDataRepository;

    public LoginPresenter(UserDataSource userDataRepository){
        //本地接收仓库父类对象
        mUserDataRepository = userDataRepository;

    }

    @Override
    public void xieYiColor(TextView textView) {
        mView.xieyiColor(textView);
    }

    @Override
    public void getVerificationCode(String phoneNumber) {
        Map<String,String> params = new HashMap<>();
        params.put("phone", phoneNumber);
        params.put("smsType", "0");

        //本地接收仓库父类对象调用 自己的方法 发送验证码
        mUserDataRepository.sendVerificationCode((RxFragment) mView,params, new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d("onSubscribe");
            }

            @Override
            public void onNext(Object o) {

                mView.verificationCodeSuccess();
            }

            @Override
            public void onError(Throwable e) {
                mView.verificationCodeFail();
                Logger.d("onError");
            }

            @Override
            public void onComplete() {
                Logger.d("onComplete");
            }
        });
    }

    @Override
    public void login(String phoneNumber, String code) {
        mView.loginSuccess();
        /*Map<String,String> params = new HashMap<>();
        params.put("phone", phoneNumber);
        params.put("verificationCode", code);
        params.put("platform", "1");
        params.put("appVersion", "1.0.0");
        params.put("deviceId", "1454564545");
        //本地接收仓库父类对象调用 自己的方法 实现登陆
        mUserDataRepository.login((RxFragment) mView, params, new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                mView.loginSuccess(user);
            }

            @Override
            public void onError(Throwable e) {
                mView.loginFail(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });*/
    }


    @Override
    public void attachView(LoginContract.View baseView) {
        if(baseView instanceof RxFragment){
            mView = baseView;
        }

    }

    @Override
    public void dettachView() {
        mView = null;
    }
}
