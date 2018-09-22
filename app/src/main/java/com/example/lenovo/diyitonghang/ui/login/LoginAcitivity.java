package com.example.lenovo.diyitonghang.ui.login;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.data.source.UserDataRepository;
import com.example.lenovo.diyitonghang.base.BaseActivity;
import com.example.lenovo.diyitonghang.data.source.UserDataSource;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class LoginAcitivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        //SDK版本判断
        sdkBanben();
        //presenter层
        //利用单例模式返回数据仓库实例
        //向下转型，父类引用指向子类对象
        UserDataSource instance = UserDataRepository.getInstance();
        //数据仓库实例，传入LoginPresenter构造中
        LoginPresenter loginPresenter = new LoginPresenter(instance);

        addFragment(LoginFragment.class, loginPresenter, R.id.login_container, null, null);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    private void sdkBanben() {
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
    }

}
