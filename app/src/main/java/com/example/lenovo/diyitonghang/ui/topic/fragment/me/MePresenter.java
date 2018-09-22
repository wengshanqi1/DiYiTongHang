package com.example.lenovo.diyitonghang.ui.topic.fragment.me;


import com.example.lenovo.diyitonghang.data.source.UserDataSource;

public class MePresenter implements MeContract.Presenter {
    private final UserDataSource mUserDataRepository;
    public MeContract.View mView;

    public MePresenter(UserDataSource userDataRepository){
        //本地接收仓库父类对象
        mUserDataRepository = userDataRepository;

    }

    @Override
    public void attachView(MeContract.View view) {
        mView = view;
    }

    @Override
    public void dettachView() {
        mView = null;
    }
}
