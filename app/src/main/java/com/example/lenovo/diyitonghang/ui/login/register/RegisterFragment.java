package com.example.lenovo.diyitonghang.ui.login.register;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.base.BaseFragment;
import com.example.lenovo.diyitonghang.view.EditImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    private View view;
    private RegisterContract.Presenter mPresenter;
    private ImageView mImageView;
    private ImageView mImageView7;
    private TextView mTextView2;
    private EditText mEditText3;
    private ImageView mImageView8;
    private TextView mTextView8;
    private int CAMERA_REQUEST_COOE = 1;
    private Button mLoginRegisterBtnEditePicOk;
    private Button mLoginRegisterBtnEditePicCancle;
    private FrameLayout mLoginRegisterEditePic;
    private Group mGroupEditPhoto;
    private Group mGroupRegister;
    private EditImageView mEditImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);

        initView(view);

        mImageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showDiaLog();
            }
        });
        return view;
    }

    private void initView(View view) {
        mImageView = (ImageView) view.findViewById(R.id.imageView);
        mImageView7 = (ImageView) view.findViewById(R.id.imageView7);
        mTextView2 = (TextView) view.findViewById(R.id.textView2);
        mEditText3 = (EditText) view.findViewById(R.id.editText3);
        mImageView8 = (ImageView) view.findViewById(R.id.imageView8);
        mTextView8 = (TextView) view.findViewById(R.id.textView8);
        mLoginRegisterBtnEditePicOk = (Button) view.findViewById(R.id.login_register_btn_edite_pic_ok);
        mLoginRegisterBtnEditePicCancle = (Button) view.findViewById(R.id.login_register_btn_edite_pic_cancle);
        mLoginRegisterEditePic = (FrameLayout) view.findViewById(R.id.login_register_edite_pic);
        mGroupEditPhoto = (Group) view.findViewById(R.id.group);
        mGroupRegister = (Group) view.findViewById(R.id.group2);
        mGroupRegister.setVisibility(View.VISIBLE);
        mGroupEditPhoto.setVisibility(View.GONE);
        mLoginRegisterBtnEditePicOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveBitmap(mEditImageView.getCircleBitmap());
            }
        });

        mTextView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onTakePhotoSuccess(String filePath) {
        mGroupEditPhoto.setVisibility(View.VISIBLE);
        mGroupRegister.setVisibility(View.INVISIBLE);
        mEditImageView = new EditImageView(getActivity(), filePath);
        mLoginRegisterEditePic.addView(mEditImageView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

    }

    @Override
    public void onTakePhotoFail(String msg) {
        Toast.makeText(baseActivity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveBitmapSuccess(String filePath) {
        mGroupEditPhoto.setVisibility(View.INVISIBLE);
        mGroupRegister.setVisibility(View.VISIBLE);
        Glide.with(this).load(filePath).into(mImageView7);
    }

    @Override
    public void onSaveBitmapFail(String msg) {
        Toast.makeText(baseActivity, "保存剪切图片失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showXiangCe(String uri) {
        Glide.with(this).load(uri).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mImageView7);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }
}
