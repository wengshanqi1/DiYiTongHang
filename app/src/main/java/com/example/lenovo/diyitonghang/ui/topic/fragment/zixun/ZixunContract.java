package com.example.lenovo.diyitonghang.ui.topic.fragment.zixun;

import com.example.lenovo.diyitonghang.base.BasePresenter;
import com.example.lenovo.diyitonghang.base.BaseView;
import com.example.lenovo.diyitonghang.data.Channel;

import java.util.List;

public interface ZixunContract {
    public interface View extends BaseView<ZixunContract.Presenter> {

        void onChannelsSuccess(List<Channel> channels);
        void onChannelsFail(String msg);

    }



    public  interface Presenter extends BasePresenter<ZixunContract.View> {

        void getChannels();

    }
}
