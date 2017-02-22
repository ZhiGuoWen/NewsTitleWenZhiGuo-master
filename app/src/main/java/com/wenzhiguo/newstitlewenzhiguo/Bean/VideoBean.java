package com.wenzhiguo.newstitlewenzhiguo.Bean;

/**
 * Created by dell on 2017/1/14.
 */

public class VideoBean {
    private String title;
    private String mp4_url;
    private String topicImg;
    private String topicName;
    private String cover;
    private String playCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMp4_url() {
        return mp4_url;
    }

    public void setMp4_url(String mp4_url) {
        this.mp4_url = mp4_url;
    }

    public String getTopicImg() {
        return topicImg;
    }

    public void setTopicImg(String topicImg) {
        this.topicImg = topicImg;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "title='" + title + '\'' +
                ", mp4_url='" + mp4_url + '\'' +
                ", topicImg='" + topicImg + '\'' +
                ", topicName='" + topicName + '\'' +
                ", cover='" + cover + '\'' +
                ", lenth='" + playCount + '\'' +
                '}';
    }
}
