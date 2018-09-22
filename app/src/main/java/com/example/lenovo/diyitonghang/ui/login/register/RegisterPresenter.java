package com.example.lenovo.diyitonghang.ui.login.register;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.base.MyObserver;
import com.example.lenovo.diyitonghang.ui.acticity.MainActivity;
import com.example.lenovo.diyitonghang.utils.Logger;
import com.example.lenovo.diyitonghang.utils.SystemFacade;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;



public class RegisterPresenter implements RegisterContract.Presenter {

    private static final String TAG = "RegisterPresenter";

    private static final int CAMERA_REQUEST_CODE = 0x100;
    private static final int GALLERY_REQUEST_CODE = 0x200;
    private RegisterContract.View mView;


    private String mImageFilePath;
    private String mAvatarFilePath;
    private Dialog dialog;


    @Override
    public void showDiaLog() {
        dialog = new Dialog(mView.getActivity(), R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(mView.getActivity()).inflate(R.layout.dialog_layout, null);
        //初始化控件
        TextView choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        TextView takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhotoFromGallery();
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoFromCamera();
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }
    @Override
    public void takePhotoFromCamera() {
        checkIfNeedRequestPermission();
    }
    @Override
    public void getPhotoFromGallery() {
//调用相册
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Fragment) mView).startActivityForResult(intent,GALLERY_REQUEST_CODE);
        dialog.dismiss();
    }
    @Override
    public void saveBitmap(Bitmap bitmap) {

        Observable.just(bitmap).flatMap(new Function<Bitmap, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Bitmap bitmap) throws Exception {
                File cacheDir = SystemFacade.getExternalCacheDir(mView.getActivity());
                if (cacheDir == null) {
                    return Observable.error(new FileNotFoundException( " 手机内部存储不可用"));
                }
                File avatar = new File(cacheDir, System.currentTimeMillis() + "circle_head.jpeg");
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(avatar);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    return Observable.just(avatar.getAbsolutePath());
                } catch (Exception e){
                    return Observable.error(e);
                }finally{
                    if(outputStream != null){
                        outputStream.close();
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<String>(){
                    @Override
                    public void onNext(String s) {
                        mView.onSaveBitmapSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onSaveBitmapFail(e.getMessage());
                    }
                });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Logger.d("%s request permission call back,requestCode = %s", TAG, requestCode);
        if (Logger.DEBUG_D) {
            for (int i = 0; i < permissions.length; i++) {
                Logger.d("      %s %s = %s", TAG, permissions[i], grantResults[i]);
            }
        }
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(mView.getActivity(), Manifest.permission.CAMERA)) {
                        showRequestPermissionAlertDialog(true);
                    } else {
                        showRequestPermissionAlertDialog(false);
                    }
                }
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                File image = new File(mImageFilePath);
                if(image.exists()){
                    mView.onTakePhotoSuccess(mImageFilePath);
                }else{
                    mView.onTakePhotoFail("未知异常");
                }
            }
            case GALLERY_REQUEST_CODE:{
                //获取图片路径
                if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = mView.getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    String imagePath = c.getString(columnIndex);
                    showImage(imagePath);
                    c.close();
                }
            }
        }

    }

    private void showImage(String imagePath) {
        mView.showXiangCe(imagePath);
    }

    @Override
    public void attachView(RegisterContract.View baseView) {
        mView = baseView;
    }

    @Override
    public void dettachView() {
        mView = null;
    }

    private void checkIfNeedRequestPermission() {
        if (!SystemFacade.hasPermission(mView.getActivity(), Manifest.permission.CAMERA)) {
            Logger.d("%s App  has no camera permission ,so need to request permission ", TAG);
            requestPermission();
        } else {
            Logger.d("%s has camera permission ", TAG);
            takePhoto();
        }
    }


    private void takePhoto() {
        File cacheDir = SystemFacade.getExternalCacheDir(mView.getActivity());
        if (cacheDir == null) {
            mView.onTakePhotoFail("手机存储空间有异常不能进行拍照");
            return;
        }


        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File head_pic = new File(cacheDir, System.currentTimeMillis() + "_head.jpeg");
        Uri photoUri;

        mImageFilePath = head_pic.getAbsolutePath();
        if (SystemFacade.hasN()) {
            photoUri = FileProvider.getUriForFile(mView.getActivity(), mView.getActivity().getPackageName() + ".fileProvider", head_pic);
        } else {
            photoUri = Uri.fromFile(head_pic);
        }

        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

        ((Fragment) mView).startActivityForResult(takePhotoIntent, CAMERA_REQUEST_CODE);
        dialog.dismiss();

    }

    private void requestPermission() {
        ((Fragment) mView).requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }

    private void showRequestPermissionAlertDialog(final boolean flag) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mView.getActivity());

        builder.setTitle(mView.getActivity().getString(R.string.login_register_request_permission_dialog_title));
        if(flag){
            builder.setMessage(mView.getActivity().getString(R.string.login_register_request_permission_dialog_description));
        }else{
            builder.setMessage(mView.getActivity().getString(R.string.login_register_request_permission_dialog_description_2));
        }

        builder.setPositiveButton(R.string.login_register_request_permission_dialog_request_permission, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(flag){
                    requestPermission(); // 再次请求用户授权
                }else{
                    startSystemPermissionActivity(); // 打开app 详细设置界面
                }
            }
        });

        builder.setNegativeButton(R.string.login_register_request_permission_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    /**
     * 打开系统app 详情页面进行手动打开权限
     */
    private void startSystemPermissionActivity() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.fromParts("package", mView.getActivity().getPackageName(), null));
        mView.getActivity().startActivity(intent);

    }
}
