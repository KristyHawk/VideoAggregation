package com.video.testtask.video_data_aggregation.dto;

/**
 * Represents a camera element after processing
 */
public class ProcessedCameraElement {

    private int id;
    private UrlType urlType;
    private String videoUrl;
    private String tokenValue;
    private String ttl;

    public ProcessedCameraElement() {
    }

    public ProcessedCameraElement(int id, UrlType urlType, String videoUrl, String tokenValue, String ttl) {
        this.id = id;
        this.urlType = urlType;
        this.videoUrl = videoUrl;
        this.tokenValue = tokenValue;
        this.ttl = ttl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }
}
