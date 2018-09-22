package com.example.lenovo.diyitonghang.data.source;

import com.example.lenovo.diyitonghang.data.Comment;
import com.example.lenovo.diyitonghang.data.Data;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.ListComment;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/*
 * created by taofu on 2018/9/5
 **/
public interface DetailsDataSource {


    Observable<DetailsData> getNewsDetailsData(String params);
    Observable<List<Data>> getNewsRelevantData(String params);

    Observable<List<Comment>> getListCommentData(String params);

    Observable<Object> getCommentData(Map<String,String> map);

}
