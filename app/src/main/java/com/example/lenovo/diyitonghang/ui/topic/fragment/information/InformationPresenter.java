package com.example.lenovo.diyitonghang.ui.topic.fragment.information;

import com.example.lenovo.diyitonghang.data.source.UserDataSource;

public class InformationPresenter implements InformationContract.Presenter {
    private final UserDataSource mUserDataRepository;
    public InformationContract.View mView;


    public InformationPresenter(UserDataSource userDataRepository){
        //本地接收仓库父类对象
        mUserDataRepository = userDataRepository;

    }

    @Override
    public void attachView(InformationContract.View view) {
        mView = view;
    }

    @Override
    public void dettachView() {
        mView = null;
    }
}
