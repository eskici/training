package com.moss.project.eneasy.web;

import com.moss.project.eneasy.web.request.DeleteEntryRequest;
import com.moss.project.eneasy.web.response.BaseResponse;
import com.moss.project.eneasy.service.EntryService;
import com.moss.project.eneasy.entity.Entry;
import com.moss.project.eneasy.web.request.AddEntryRequest;
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
@RequestMapping(value = "/entry")
@Api(value = "/entry")
@AllArgsConstructor
public class EntryController {

    private EntryService entryService;

    @PostMapping("/addEntry")
    @ApiOperation(value = "Adds new entry")
    public void addEntry(@ApiParam(value = "Adding Entry request model", required = true) @RequestBody AddEntryRequest addEntryRequest) {
        String content = addEntryRequest.getContent();
        Long topicId = addEntryRequest.getEntryId();
        entryService.addEntry(topicId,content);
    }

    @PutMapping("/updateEntry")
    @ApiOperation(value = "Updates entry")
    public void updateEntry(@ApiParam(value = "Update Entry request model", required = true) @RequestBody AddEntryRequest addEntryRequest) {
        String content = addEntryRequest.getContent();
        Long entryId = addEntryRequest.getEntryId();
        entryService.updateEntry(entryId, content);
    }

    @PutMapping("/deleteEntry")
    @ApiOperation(value = "Deletes entry")
    public void deleteEntry(@ApiParam(value = "Delete Entry request model", required = true) @RequestBody DeleteEntryRequest deleteEntryRequest) {
        Long entryId = deleteEntryRequest.getEntryId();
        entryService.deleteEntry(entryId);
    }

    @PostMapping("/listMyEntries")
    @ApiOperation(value = "List user's entries")
    public ResponseEntity<BaseResponse<List>> listMyEntries() {
        List<Entry> entries= entryService.listMyEntries();
        return ResponseEntity.ok(new BaseResponse<List>(entries));
    }

    @GetMapping("/listEntries")
    @ApiOperation(value = "List entries of the topic", notes = "order by createDate desc")
    public ResponseEntity<BaseResponse<List>> listEntries(@ApiParam(value = "Topic Id", required = true) @RequestParam(value = "topicId") Long topicId,
                                                          @RequestParam(value = "pageIndex") Integer pageIndex){
        List<Entry> entries= entryService.listEntries(topicId, pageIndex);
        return ResponseEntity.ok(new BaseResponse<List>(entries));
    }

}
