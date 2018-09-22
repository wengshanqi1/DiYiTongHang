package com.example.lenovo.diyitonghang.data.source.remote.retrofit;


import com.example.lenovo.diyitonghang.data.ChannelData;
import com.example.lenovo.diyitonghang.data.Data;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.HttpResult;
import com.example.lenovo.diyitonghang.data.ListComment;
import com.example.lenovo.diyitonghang.data.News;
import com.example.lenovo.diyitonghang.data.NewsData;
import com.example.lenovo.diyitonghang.data.NewsDetailsData;
import com.example.lenovo.diyitonghang.data.RelevantData;
import com.example.lenovo.diyitonghang.data.User;
import com.example.lenovo.diyitonghang.data.UploadPhotoBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/*
 * created by taofu on 2018/8/29
 **/
public interface FirstgaService {

    @POST("users/phoneLogin")
    Observable<HttpResult<User>> login(@Body Map<String, String> params);


    @POST("users/sendVerificationCode")
    Observable<HttpResult<Object>> sendVerificationCode(@Body Map<String, String> params);

    @Multipart
    @POST("/users/uploadHeadImage")
    Observable<HttpResult<UploadPhotoBean>> uploadPhoto(@Part("userId") String user, @Part("headImageFile") MultipartBody file);


    @POST("news/listNewsChannel")
    Observable<HttpResult<ChannelData>> loadChannels();

    @POST("news/upListNews")
    Observable<HttpResult<NewsData>> fetchNewNews(@Body Map<String, String> params);


    @FormUrlEncoded
    @POST("news/info")
    Observable<HttpResult<DetailsData>> newsDetails(@Field("newsId") String newId,@Field("userId") String userId);


    @FormUrlEncoded
    @POST("news/relevant")
    Observable<HttpResult<List<Data>>> getRelevant(@Field("newsId") String newId);

    @FormUrlEncoded
    @POST("users/listComment")
    Observable<HttpResult<ListComment>> getListComment(@Field("userId") String userId);

    @POST("users/comment")
    Observable<HttpResult<Object>> getCommentData(@Body Map<String,String> map);

}
