package com.video.testtask.video_data_aggregation.controller;

import com.video.testtask.video_data_aggregation.handlers.error.InvalidRequestJsonException;
import com.video.testtask.video_data_aggregation.helper.RequestHelper;
import com.video.testtask.video_data_aggregation.service.ProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Controller for video data aggregation
 */

@Controller
@RequestMapping("/api")
public class VideoAggregationController {

    private final ProcessingService processingService;

    @Autowired
    public VideoAggregationController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processVideoElements(@RequestBody String camerasInfoJsonToProcess)
            throws InterruptedException, IOException, InvalidRequestJsonException {
        String result = processingService.processCameraList(camerasInfoJsonToProcess);
        return new ResponseEntity<>(result, RequestHelper.buildJsonHttpHeader(), HttpStatus.OK);
    }
}
