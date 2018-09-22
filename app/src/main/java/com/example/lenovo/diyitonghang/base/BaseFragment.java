package com.example.lenovo.diyitonghang.base;


import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.example.lenovo.diyitonghang.R;
import com.trello.rxlifecycle2.components.support.RxFragment;

public class BaseFragment extends RxFragment {

    protected BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity= (BaseActivity) context;
    }

    public boolean isNeedAddToBackStack() {
        return true;
    }

    public boolean isNeedHidePreFragment() {
        return true;
    }

//    enter 指定向栈中放入新的Fragment时的动画
    public int getEnterAnimId() {
        return R.anim.actionsheet_fragment_in;
    }

    //exit 指定向栈中弹出当前栈顶的Fragment时的动画
    public int getPreExistAnimId() {
        return R.anim.actionsheet_fragment_out;
    }

    //popEnter 指定由于当前栈顶Fragment弹出而显示底层的Fragment时的动画
    public int getPopPreEnterAnimId() {
        return R.anim.actionsheet_fragment_into;
    }

    //popExit 指定当前栈顶的Fragment被弹出时的动画
    public int getPopExistAnimId() {
        return  R.anim.actionsheet_fragment_outto;
    }


}

