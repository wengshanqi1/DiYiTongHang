package com.example.lenovo.diyitonghang.data.source;


import com.example.lenovo.diyitonghang.data.Channel;
import com.example.lenovo.diyitonghang.data.User;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;

/*
 * created by taofu on 2018/8/29
 **/
public interface UserDataSource {
   void login(RxFragment fragment, Map<String, String> params, Observer<User> observer);

   void sendVerificationCode(RxFragment fragment, Map<String, String> params, Observer<Object> observer);
}
