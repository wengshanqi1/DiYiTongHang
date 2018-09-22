package com.example.lenovo.diyitonghang.ui.login;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lenovo.diyitonghang.base.BaseView;
import com.example.lenovo.diyitonghang.base.BasePresenter;
import com.example.lenovo.diyitonghang.data.User;

public interface LoginContract {

    public interface View extends BaseView<LoginContract.Presenter>{

        void verificationCodeSuccess();

        void verificationCodeFail();

        void xieyiColor(TextView textView);

        void loginSuccess();

        void loginFail(String msg);

    }



    public  interface Presenter extends BasePresenter<LoginContract.View>{

        //设置用户协议颜色
        void xieYiColor(TextView textView);

        //发送验证码
        void getVerificationCode(String phoneNumber);

        //登录
        void login(String phoneNumber,String code);
    }

}
