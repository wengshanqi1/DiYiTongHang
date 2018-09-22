package com.example.lenovo.diyitonghang.ui.topic.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.diyitonghang.R;
import com.example.lenovo.diyitonghang.data.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHodler> {
    private static final String TAG = "CommentExpandAdapter";
    private List<Comment> commentBeanList;
    private Context context;

    public CommentAdapter(Context context, List<Comment> commentBeanList)               {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }


    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodler(LayoutInflater.from(context).inflate(R.layout.adapter_comment,null));
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {
        holder.mCommentItemUserName.setText(commentBeanList.get(position).getCommentId());
        holder.mCommentItemTime.setText(commentBeanList.get(position).getCommentTime());
        holder.mCommentItemContent.setText(commentBeanList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return commentBeanList.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        TextView mCommentItemContent;
        TextView mCommentItemTime;
        TextView mCommentItemUserName;

        public ViewHodler(View itemView) {
            super(itemView);

            mCommentItemUserName = itemView.findViewById(R.id.comment_item_userName);
            mCommentItemTime = itemView.findViewById(R.id.comment_item_time);
            mCommentItemContent = itemView.findViewById(R.id.comment_item_content);
        }
    }
}
