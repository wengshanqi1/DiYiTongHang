package com.example.lenovo.diyitonghang.data;

import java.util.List;

public class ListComment {

    private List<Comment> userCommentList;

    public ListComment(List<Comment> userCommentList) {
        this.userCommentList = userCommentList;
    }

    public List<Comment> getUserCommentList() {
        return userCommentList;
    }

    public void setUserCommentList(List<Comment> userCommentList) {
        this.userCommentList = userCommentList;
    }
}
