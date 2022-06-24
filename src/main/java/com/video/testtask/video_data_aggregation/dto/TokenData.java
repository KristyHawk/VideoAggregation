package com.video.testtask.video_data_aggregation.dto;

public class TokenData {

    private String value;
    private String ttl;

    public TokenData() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }
}
