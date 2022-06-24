package com.video.testtask.video_data_aggregation.controller;

import com.video.testtask.video_data_aggregation.handlers.error.InvalidRequestJsonException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoAggregationControllerTest {

    private static final String VALID_REQUEST_BODY_EXAMPLE =
                    "[\n" +
                    "   {\n" +
                    "        \"id\": 1,\n" +
                    "        \"sourceDataUrl\": \"http://www.mocky.io/v2/5c51b230340000094f129f5d\",\n" +
                    "        \"tokenDataUrl\": \"http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s\"\n" +
                    "    }\n" +
                    "]";

    private static final String INVALID_REQUEST_BODY_EXAMPLE =
                    "[\n" +
                    "   {\n" +
                    "        \"id\": 1\n" +
                    "        \"sourceDataUrl\": \"http://www.mocky.io/v2/5c51b230340000094f129f5d\"\n" +
                    "        \"tokenDataUrl\": \"http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s\"\n" +
                    "    }\n" +
                    "]";

    private static final String VALID_RESPONSE_JSON = "[{\"id\":1,\"urlType\":\"LIVE\",\"videoUrl\":\"rtsp://127.0.0.1/1\",\"tokenValue\":\"fa4b588e-249b-11e9-ab14-d663bd873d93\",\"ttl\":\"120\"}]";

    private static final String URL_TEMPLATE = "/api/process";

    private MockMvc mockMvc;

    @Autowired
    private VideoAggregationController controller;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testValidStatus() throws Exception {
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_REQUEST_BODY_EXAMPLE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testInvalidJsonInRequest() throws Exception {
        mockMvc.perform(post(URL_TEMPLATE, INVALID_REQUEST_BODY_EXAMPLE)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testExpectedResults() throws Exception {
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_REQUEST_BODY_EXAMPLE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(VALID_RESPONSE_JSON));
    }

    @Test(expected = InvalidRequestJsonException.class)
    public void testErrorInCaseOfInvalidJson() throws Exception {
        controller.processVideoElements(INVALID_REQUEST_BODY_EXAMPLE);
    }
}
