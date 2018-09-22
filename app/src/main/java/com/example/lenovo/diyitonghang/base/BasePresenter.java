package com.example.lenovo.diyitonghang.base;

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void dettachView();
}
