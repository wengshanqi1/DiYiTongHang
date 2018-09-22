package com.example.lenovo.diyitonghang.data;

import java.util.List;

public class Comment {
    private String commentId;
    private String commentTime;
    private String content;
    private String objectId;
    private String objectType;
    private String title;

    public Comment(String commentId, String commentTime, String content, String objectId, String objectType, String title) {
        this.commentId = commentId;
        this.commentTime = commentTime;
        this.content = content;
        this.objectId = objectId;
        this.objectType = objectType;
        this.title = title;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
