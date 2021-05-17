package com.moss.project.eneasy.web;

import com.moss.project.eneasy.entity.Entry;
import com.moss.project.eneasy.enums.EnumStatus;
import com.moss.project.eneasy.entity.Topic;
import com.moss.project.eneasy.web.response.BaseResponse;
import com.moss.project.eneasy.service.EntryService;
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
@RequestMapping(value = "/admin")
@Api(value = "/admin")
@AllArgsConstructor
public class AdminController {

    private TopicService topicService;

    private EntryService entryService;

    @PutMapping("/approveTopic")
    @ApiOperation(value = "Approve topic", notes = "Admin approves waiting topics")
    public ResponseEntity<BaseResponse<String>> approveTopic(@ApiParam(value = "Bulk receipt request model", required = true) @RequestParam(value = "topicId") Long topicId) {
        topicService.updateTopicStatus(topicId, EnumStatus.APPROVED);
        return ResponseEntity.ok(new BaseResponse<String>("OK"));
    }

    @PutMapping("/cancelTopic")
    @ApiOperation(value = "Cancel topic", notes = "Admin cancels waiting topics")
    public ResponseEntity<BaseResponse<String>> cancelTopic(@ApiParam(value = "Bulk receipt request model", required = true) @RequestParam(value = "topicId") Long topicId){
        topicService.updateTopicStatus(topicId, EnumStatus.CANCELLED);
        return ResponseEntity.ok(new BaseResponse<String>("OK"));
    }

    @PutMapping("/approveEntry")
    @ApiOperation(value = "Approve entry", notes = "Admin approves waiting entry")
    public ResponseEntity<BaseResponse<String>> approveEntry(@ApiParam(value = "Entry ID", required = true) @RequestParam(value = "entryId") Long entryId) {
        entryService.updateEntryStatus(entryId, EnumStatus.APPROVED);
        return ResponseEntity.ok(new BaseResponse<String>("OK"));
    }

    @PutMapping("/cancelEntry")
    @ApiOperation(value = "Cancel entry", notes = "Admin cancels waiting entry")
    public ResponseEntity<BaseResponse<String>> cancelEntry(@ApiParam(value = "Entry ID", required = true) @RequestParam(value = "entryId") Long entryId){
        entryService.updateEntryStatus(entryId, EnumStatus.CANCELLED);
        return ResponseEntity.ok(new BaseResponse<String>("OK"));
    }

    @GetMapping("/listWaitingTopics")
    @ApiOperation(value = "List waiting topics", notes = "Admin views waiting topics")
    public ResponseEntity<BaseResponse<List>> listWaitingTopics() {
        List<Topic> topics = topicService.listWaitingTopics();
        return ResponseEntity.ok(new BaseResponse<>(topics));
    }

    @GetMapping("/listWaitingEntries")
    @ApiOperation(value = "List waiting entries", notes = "Admin views waiting entries")
    public ResponseEntity<BaseResponse<List>> listWaitingEntries(@RequestParam(value = "pageIndex") Integer pageIndex) {
        List<Entry> entries = entryService.listWaitingEntrys(pageIndex);
        return ResponseEntity.ok(new BaseResponse<>(entries));
    }
}
