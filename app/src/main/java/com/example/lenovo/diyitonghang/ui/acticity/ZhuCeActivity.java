package com.example.lenovo.diyitonghang.ui.acticity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.lenovo.diyitonghang.R;

import java.io.File;

public class ZhuCeActivity extends AppCompatActivity /*implements View.OnClickListener*/ {

    /*private static final int IMAGE = 1;
    private static final int XIANGCE = 2;
    private String path;
    private Dialog dialog;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        path = getPath() + "/image.png";
    }

    //头像点击事件
    public void onClick(View view) {
        //选择手机相机，还是手机相册
        show();
    }

    private void show() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        //初始化控件
        TextView choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        TextView takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //加载图片
                *//*if (file.exists()) {
                    //对相机拍照照片进行裁剪
                    photoClip(Uri.fromFile(file));
                }*//*
                startActivityForResult(intent, XIANGCE);
            }
        });
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用系统相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //指定拍照之后文件存储路径
                Uri uri = Uri.fromFile(new File(path));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                加载图片
                if (file.exists()) {
                    //对相机拍照照片进行裁剪
                    photoClip(Uri.fromFile(file));
                }
                startActivityForResult(intent, IMAGE);
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
    public void onBackPressed() {
        super.onBackPressed();
        if (dialog!=null)dialog.cancel();
        this.finish();
    }

    //头像获取成功的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.cancel();
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE) {
            showImage(path);
        }
        //获取图片路径
        if (requestCode == XIANGCE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }

    //加载图片
    private void showImage(String imaePath) {
        ImageView imageView = findViewById(R.id.imageView7);
        Glide.with(this).load(imaePath).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
//        ((ImageView) findViewById(R.id.imageView7)).setImageBitmap(bm);

        dialog.cancel();
    }

    public String getPath() {
        file = new File(Environment.getExternalStorageDirectory().getPath() + "/wkk");
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return file.getPath();
    }
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent,XIANGCE );
    }*/


}
