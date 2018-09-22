package com.example.lenovo.diyitonghang.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.base.BaseFragment;
import com.example.lenovo.diyitonghang.data.User;
import com.example.lenovo.diyitonghang.ui.login.register.RegisterContract;
import com.example.lenovo.diyitonghang.ui.login.register.RegisterFragment;
import com.example.lenovo.diyitonghang.ui.login.register.RegisterPresenter;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/*
 * created by taofu on 2018/8/30
 **/
public class LoginFragment extends BaseFragment implements LoginContract.View {
    private LoginContract.Presenter mPresenter;
    private TextView mTvSendCode;
    private TextView loginLoginTvLicense;
    private EditText mEtvPhoneNumber;
    private EditText mEtvVerificationCode;
    private CheckBox mCbLisence;
    private Button mBtnLoggin;
    private ImageView qqmImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        bindToLifecycle();

        View view = inflater.inflate(R.layout.fragment_login ,container,false );

        mTvSendCode = view.findViewById(R.id.login_btn_send_verification_code);

        mEtvPhoneNumber = view.findViewById(R.id.login_etv_phone_number);
        mEtvVerificationCode = view.findViewById(R.id.login_etv_verification_code);

        loginLoginTvLicense = view.findViewById(R.id.login_login_tv_license);
        qqmImageView = view.findViewById(R.id.imageView2);
        mCbLisence = view.findViewById(R.id.login_cb_license);
        qqmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAcitivity context = (LoginAcitivity) getContext();
                UMShareConfig config = new UMShareConfig();
                config.isNeedAuthOnGetUserInfo(true);
                UMShareAPI.get(context).setShareConfig(config);
                UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.QQ, authListener);
            }
        });

        mBtnLoggin = view.findViewById(R.id.login_btn_login);
        mEtvPhoneNumber.addTextChangedListener(new MyTextWatcher());
        mEtvVerificationCode.addTextChangedListener(new MyTextWatcher());
        mCbLisence.setOnCheckedChangeListener(new MyCheckBox());

        mTvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = mEtvPhoneNumber.getText().toString();
                if (!phoneNumber.matches("[1][3,4,5,7,8][0-9]{9}")){
                    Toast.makeText(getActivity(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }else{
                    mPresenter.getVerificationCode(phoneNumber);
                }
                //TODO 需要做手机号校验

            }
        });


        mBtnLoggin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = mEtvPhoneNumber.getText().toString();
                String code = mEtvVerificationCode.getText().toString();
                mPresenter.login(phoneNumber, code);

            }
        });
        mPresenter.xieYiColor(loginLoginTvLicense);
        return view;

    }
    //第三方登录回调
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }
        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getActivity(), "成功了", Toast.LENGTH_LONG).show();
        }
        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getActivity(), "失败：" + t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getActivity(), "取消了", Toast.LENGTH_LONG).show();
        }
    };
    //实时监听EditText变化
    private class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (mEtvPhoneNumber.getText().length()!=0&&mEtvVerificationCode.getText().length()!=0&&mCbLisence.isChecked()){
                mBtnLoggin.setBackgroundResource(R.drawable.btn_true);
                mBtnLoggin.setEnabled(true);
            }else{
                mBtnLoggin.setBackgroundResource(R.drawable.common_round_corner_bg_color_a);
                mBtnLoggin.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    //实时监听chenkBox变化
    private class MyCheckBox implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (mEtvPhoneNumber.getText().length()!=0&&mEtvVerificationCode.getText().length()!=0&&mCbLisence.isChecked()){
                mBtnLoggin.setBackgroundResource(R.drawable.btn_true);
                mBtnLoggin.setEnabled(true);
            }else{
                mBtnLoggin.setBackgroundResource(R.drawable.common_round_corner_bg_color_a);
                mBtnLoggin.setEnabled(false);
            }
        }
    }

    @Override
    public void verificationCodeSuccess() {
        mTvSendCode.setText(R.string.login_verification_code_send_ok);
    }

    @Override
    public void verificationCodeFail() {
        mTvSendCode.setText(R.string.login_verification_code_send_fail);
    }

    @Override
    public void xieyiColor(TextView textView) {
        SpannableString spannableString = new SpannableString(textView.getText().toString());
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#3885af")), 7, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
    @Override
    public void loginSuccess() {
        RegisterFragment registerFragment= new RegisterFragment();
        Bundle args = new Bundle();
        registerFragment.setArguments(args);
        RegisterPresenter registerPresenter = new RegisterPresenter();
        baseActivity.addFragment(RegisterFragment.class, registerPresenter, R.id.login_container, null, null);
        Toast.makeText(baseActivity, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail(String msg) {
        Log.e("LoginFragment", msg);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }
}
