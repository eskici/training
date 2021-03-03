package com.moss.project.eneasy.rest;

import com.moss.project.eneasy.model.Topic;
import com.moss.project.eneasy.rest.request.AddTopicRequest;
import com.moss.project.eneasy.rest.response.BaseResponse;
import com.moss.project.eneasy.service.TopicServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author Taner YILDIRIM
 */

@RestController
@RequestMapping(value = "/topic")
@Api(value = "/topic")
public class TopicController {

    @Autowired
    private TopicServiceImpl topicService;

    @PostMapping("/addTopic")
    @ApiOperation(value = "Kbn Batch Excel Upload operation", notes = "Upload Excel File")
    public ResponseEntity<BaseResponse<Long>> addTopic(@ApiParam(value = "Add topic request model", required = true) @RequestBody AddTopicRequest addTopicRequest) {
        String title = addTopicRequest.getTitle();
        String content = addTopicRequest.getContent();
        Long id = topicService.addNewTopic(title, content);
        return ResponseEntity.ok(new BaseResponse<Long>(id));
    }

    @PostMapping("/listTopics")
    @ApiOperation(value = "Lists last approved topics", notes = "Upload Excel File")
    public ResponseEntity<BaseResponse<List>> listTopics() {
        List<Topic> topics = topicService.readLastTopics();
        return ResponseEntity.ok(new BaseResponse<List>(topics));
    }
}