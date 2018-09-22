package com.example.lenovo.diyitonghang.ui.topic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.base.BaseActivity;
import com.example.lenovo.diyitonghang.data.Comment;
import com.example.lenovo.diyitonghang.data.Data;
import com.example.lenovo.diyitonghang.data.DetailsData;
import com.example.lenovo.diyitonghang.data.source.DetailsDataRepository;
import com.example.lenovo.diyitonghang.data.source.remote.DetailsRemoteDataSource;
import com.example.lenovo.diyitonghang.ui.topic.adapter.CommentAdapter;
import com.example.lenovo.diyitonghang.ui.topic.adapter.RelevantAdapter;
import com.example.lenovo.diyitonghang.ui.topic.details.DetailsContract;
import com.example.lenovo.diyitonghang.ui.topic.details.DetailsDataPresenter;
import com.example.lenovo.diyitonghang.utils.KeyMapDailog;
import com.example.lenovo.diyitonghang.utils.StatusBarManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DataDetailsActivity extends BaseActivity implements DetailsContract.DetailsView, View.OnClickListener {


    private DetailsContract.DetailsPresenter mPresenter;
    private String newsId;
    private WebView mWebView;
    private TextView mDetailsTime;
    private TextView mDetailsPingLun;
    private TextView mDetailsTitle;
    private ImageView mGenTie;
    private int code = 0;
    private RecyclerView mRecyclerDetailsViewUp;
    private RecyclerView mRecyclerDetailsViewDown;
    private CommentAdapter commentAdapter;
    private Dialog dialog;
    private List<Comment> addCommentsStart;
    private List<Comment> addCommentsMore = new ArrayList<>();
    private CommentAdapter commentAdapter1;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        StatusBarManager.immersive(this);
        initView();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Intent intent = getIntent();
        newsId = intent.getStringExtra("news");
        setPresenter(new DetailsDataPresenter(DetailsDataRepository.getInstance(DetailsRemoteDataSource.getInstance())));

        mPresenter.getDetails(newsId);
        mPresenter.getRelevantData(newsId);
        mPresenter.getListComment("");
    }

    private void show() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.detailsdialog_layout, null);
        //初始化控件
        TextView viewById = inflate.findViewById(R.id.dialog_textView6);
        final EditText EditText = inflate.findViewById(R.id.dialog_editText);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCommentData(EditText.getText().toString());
                dialog.dismiss();

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

    private void initView() {
        mDetailsTime = findViewById(R.id.details_time);
        mDetailsTitle = findViewById(R.id.details_title);
        mDetailsPingLun = findViewById(R.id.details_pinglun);
        mGenTie = findViewById(R.id.gentie);
        mGenTie.setOnClickListener(this);
        mDetailsPingLun.setOnClickListener(this);
        mRecyclerDetailsViewUp = findViewById(R.id.recycler_details_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerDetailsViewUp.setLayoutManager(linearLayoutManager);
        mRecyclerDetailsViewDown = findViewById(R.id.recycler_details_view1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerDetailsViewDown.setLayoutManager(linearLayoutManager1);


        mWebView = findViewById(R.id.webView);
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    public void setPresenter(DetailsContract.DetailsPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }


    @Override
    public void onDataDetailsSuccess(DetailsData DetailsData) {
//        String html = "<html><header>" + "</header>" + DetailsData.getContent() + "</body><ml>";
        mWebView.loadDataWithBaseURL(null, getNewContent(DetailsData.getContent()), "text/html", "utf-8", null);
        mDetailsTime.setText(DetailsData.getPublishTime());
        mDetailsTitle.setText(DetailsData.getTitle());

    }

    //172.16.47.39
    @Override
    public void onDataDetailsError(String Error) {
        Log.e("========>", "onDataDetailsError: " + Error);
    }

    @Override
    public void onDataRelevantData(List<Data> relevantData) {
        RelevantAdapter relevantAdapter = new RelevantAdapter(R.layout.adapter_right_image, relevantData);
        mRecyclerDetailsViewUp.setAdapter(relevantAdapter);
    }

    @Override
    public void showListComment(List<Comment> comments) {
        index = 5;
        addCommentsStart = new ArrayList<>();

        addCommentsMore.addAll(comments);
        for (int i = 0; i < index; i++) {
            addCommentsStart.add(i,addCommentsMore.get(i));
        }
        commentAdapter1 = new CommentAdapter(this, addCommentsStart);
        mRecyclerDetailsViewDown.setAdapter(commentAdapter1);
    }

    @Override
    public void showCommentData() {
        Toast.makeText(this,"评论成功",Toast.LENGTH_SHORT);
        mPresenter.getListComment("");
    }

    @Override
    public void showCommentError(String error) {
        Toast.makeText(this,"失败了",Toast.LENGTH_SHORT);
    }

    private String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }

        Log.d("VACK", doc.toString());
        return doc.toString();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.details_pinglun: {
                show();

                break;
            }

            case R.id.gentie: {
                for (int i =index; i <addCommentsMore.size(); i++) {
                    addCommentsStart.add(i,addCommentsMore.get(i));
                }
                commentAdapter1.notifyDataSetChanged();
            }
        }


    }
}
