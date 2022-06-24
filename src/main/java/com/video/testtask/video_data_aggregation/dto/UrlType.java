package com.video.testtask.video_data_aggregation.entity;

public enum UrlType {

    LIVE("LIVE"),
    ARCHIVE("ARCHIVE");

    public final String name;

    UrlType(String name) {
        this.name = name;
    }
}
