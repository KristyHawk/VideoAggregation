package com.video.testtask.video_data_aggregation.service;

import com.video.testtask.video_data_aggregation.handlers.error.InvalidRequestJsonException;

import java.io.IOException;

public interface ProcessingApi {

    String processCameraList(String camerasInfo) throws InvalidRequestJsonException, IOException, InterruptedException;
}
