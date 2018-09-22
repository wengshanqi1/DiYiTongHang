package com.example.lenovo.diyitonghang.ui.login.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.lenovo.diyitonghang.base.BasePresenter;
import com.example.lenovo.diyitonghang.base.BaseView;
import com.example.lenovo.diyitonghang.data.User;
import com.example.lenovo.diyitonghang.ui.login.LoginContract;

import retrofit2.http.Multipart;

public interface RegisterContract {
    public interface View extends BaseView<RegisterContract.Presenter> {

        void onTakePhotoSuccess(String filePath); // 拍照后大图的 filepath
        void onTakePhotoFail(String msg); // 拍照保存图片到指定路径失败

        void onSaveBitmapSuccess(String filePath); // 保存剪切后的原图 filePath
        void onSaveBitmapFail(String msg); // 保存剪切图片失败

        void showXiangCe(String uri);
    }



    public  interface Presenter extends BasePresenter<RegisterContract.View> {


        void showDiaLog();
        void takePhotoFromCamera(); // 通过相机拍照
        void getPhotoFromGallery(); // 通过相册获取照片
        void saveBitmap(Bitmap bitmap); // 保存剪切后的圆形bitmap

        void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
        void onActivityResult(int requestCode, int resultCode, Intent data);

    }

}
