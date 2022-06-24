package com.video.testtask.video_data_aggregation.entity;

public class SourceData {

    private UrlType urlType;
    private String videoUrl;

    public SourceData() {
    }

    public UrlType getUrlType() {
        return urlType;
    }

    public void setUrlType(UrlType urlType) {
        this.urlType = urlType;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
