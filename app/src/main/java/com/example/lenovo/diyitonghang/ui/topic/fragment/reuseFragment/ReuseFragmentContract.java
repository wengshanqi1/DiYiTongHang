package com.example.lenovo.diyitonghang.ui.topic.fragment.reuseFragment;

import com.example.lenovo.diyitonghang.base.BasePresenter;
import com.example.lenovo.diyitonghang.base.BaseView;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.NewsData;
import com.example.lenovo.diyitonghang.data.NewsDetailsData;

public interface ReuseFragmentContract {

    public static final String PARAMS_CHANNER_ID = "channelId";

    public interface View extends BaseView<ReuseFragmentContract.Presenter> {
        void onNewsListSuccess(NewsData news);
        void onNewsListFail(String msg);

    }
    public  interface Presenter extends BasePresenter<ReuseFragmentContract.View> {
        void getNewsByChannel(String channelId);
    }
}
