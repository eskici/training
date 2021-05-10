package com.moss.project.eneasy.web;

import com.moss.project.eneasy.entity.Topic;
import com.moss.project.eneasy.web.request.AddTopicRequest;
import com.moss.project.eneasy.web.response.BaseResponse;
import com.moss.project.eneasy.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Taner YILDIRIM
 */

@RestController
@RequestMapping(value = "/topic")
@Api(value = "/topic")
@AllArgsConstructor
public class TopicController {

    private TopicService topicService;

    @PostMapping("/addTopic")
    @ApiOperation(value = "Adds new topic")
    public ResponseEntity<BaseResponse<Long>> addTopic(@ApiParam(value = "Add topic request model", required = true) @RequestBody AddTopicRequest addTopicRequest) {
        String title = addTopicRequest.getTitle();
        String content = addTopicRequest.getContent();
        Long id = topicService.addTopic(title, content);
        return ResponseEntity.ok(new BaseResponse<Long>(id));
    }

    @GetMapping("/listTopics")
    @ApiOperation(value = "Lists last approved topics")
    public ResponseEntity<BaseResponse<List>> listTopics() {
        List<Topic> topics = topicService.readLastTopics();
        return ResponseEntity.ok(new BaseResponse<List>(topics));
    }
}