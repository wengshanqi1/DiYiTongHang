package com.example.lenovo.diyitonghang.ui.topic.details;

import com.example.lenovo.diyitonghang.base.BasePresenter;
import com.example.lenovo.diyitonghang.base.BaseView;
import com.example.lenovo.diyitonghang.data.Comment;
import com.example.lenovo.diyitonghang.data.Data;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.NewsData;
import com.example.lenovo.diyitonghang.data.RelevantData;

import java.util.List;
import java.util.Map;

public interface DetailsContract {

    public interface DetailsView extends BaseView<DetailsContract.DetailsPresenter>{
        void onDataDetailsSuccess(DetailsData detailsData);
        void onDataDetailsError(String Error);

        void onDataRelevantData(List<Data> relevantData);

        void showListComment(List<Comment> comments);

        void showCommentData();
        void showCommentError(String error);
    }

    public  interface DetailsPresenter extends BasePresenter<DetailsContract.DetailsView> {
        void getDetails(String newsId);

        void getRelevantData(String newsId);

        void getListComment(String newsId);

        void getCommentData(String content);

    }
}
