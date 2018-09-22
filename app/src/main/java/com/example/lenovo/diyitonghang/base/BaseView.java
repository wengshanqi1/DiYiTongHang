package com.example.lenovo.diyitonghang.base;


import android.app.Activity;

public interface BaseView <T extends BasePresenter> {

    Activity getActivity();
    void setPresenter(T presenter);
}
