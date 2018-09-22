package com.example.lenovo.diyitonghang.data;

import java.util.List;

public class Data {
    public String origin;
    public List<String> imageListThumb;
    public String layoutType;
    public String newsId;
    public int pageviews;
    public String publishTime;
    public String title;

    public Data(List<String> imageListThumb, String layoutType, String newsId, String origin, int pageviews, String publishTime, String title) {
        this.imageListThumb = imageListThumb;
        this.layoutType = layoutType;
        this.newsId = newsId;
        this.origin = origin;
        this.pageviews = pageviews;
        this.publishTime = publishTime;
        this.title = title;
    }

    public List<String> getImageListThumb() {
        return imageListThumb;
    }

    public void setImageListThumb(List<String> imageListThumb) {
        this.imageListThumb = imageListThumb;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getPageviews() {
        return pageviews;
    }

    public void setPageviews(int pageviews) {
        this.pageviews = pageviews;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
