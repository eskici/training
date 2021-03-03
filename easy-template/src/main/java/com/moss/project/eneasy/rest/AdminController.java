package com.moss.project.eneasy.rest;

import com.moss.project.eneasy.enums.TopicStatus;
import com.moss.project.eneasy.model.Topic;
import com.moss.project.eneasy.rest.response.BaseResponse;
import com.moss.project.eneasy.service.EntryServiceImpl;
import com.moss.project.eneasy.service.TopicServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Taner YILDIRIM
 */

@RestController
@RequestMapping(value = "/admin")
@Api(value = "/admin")
public class AdminController {

    @Autowired
    private TopicServiceImpl topicService;

    @Autowired
    private EntryServiceImpl entryService;

    @PutMapping("/approveTopic")
    @ApiOperation(value = "Approve topic", notes = "Admin approves waiting topics")
    public ResponseEntity<BaseResponse<String>> approveTopic(@ApiParam(value = "Bulk receipt request model", required = true) @RequestParam(value = "topicId") Long topicId) {
        topicService.changeTopicStatus(topicId, TopicStatus.APPROVED);
        return ResponseEntity.ok(new BaseResponse<String>("OK"));
    }

    @PutMapping("/cancelTopic")
    @ApiOperation(value = "Cancel topic", notes = "Admin cancels waiting topics")
    public ResponseEntity<BaseResponse<String>> cancelTopic(@ApiParam(value = "Bulk receipt request model", required = true) @RequestParam(value = "topicId") Long topicId){
        topicService.changeTopicStatus(topicId, TopicStatus.CANCELLED);
        return ResponseEntity.ok(new BaseResponse<String>("OK"));
    }

    @PutMapping("/listWaitingTopics")
    @ApiOperation(value = "List waiting topics", notes = "Admin views waiting topics")
    public ResponseEntity<BaseResponse<List>> listWaitingTopics() {
        List<Topic> topics = topicService.listWaitingTopics();
        return ResponseEntity.ok(new BaseResponse<List>(topics));
    }
}
