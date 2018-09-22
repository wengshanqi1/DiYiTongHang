package com.example.lenovo.diyitonghang.ui.topic.fragment.me;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

}
