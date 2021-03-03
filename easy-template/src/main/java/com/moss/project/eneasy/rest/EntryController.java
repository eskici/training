package com.moss.project.eneasy.rest;

import com.moss.project.eneasy.rest.response.BaseResponse;
import com.moss.project.eneasy.service.EntryServiceImpl;
import com.moss.project.eneasy.model.Entry;
import com.moss.project.eneasy.rest.request.AddEntryRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Taner YILDIRIM
 */

@RestController
@RequestMapping(value = "/entry")
@Api(value = "/entry")
public class EntryController {

    @Autowired
    private EntryServiceImpl entryService;

    @PostMapping("/addEntry")
    @ApiOperation(value = "Kbn Batch Excel Upload operation", notes = "Upload Excel File")
    public void addEntry(@ApiParam(value = "Bulk receipt request model", required = true) @RequestBody AddEntryRequest addEntryRequest) throws IOException{
        String content = addEntryRequest.getContent();
        Long topicId = addEntryRequest.getTopicId();
        entryService.addNewEntry(topicId,content);
    }

    @PostMapping("/listMyEntries")
    @ApiOperation(value = "List user's entries", notes = "Upload Excel File")
    public ResponseEntity<BaseResponse<List>> listMyEntries(){
        List<Entry> entries= entryService.readMyEntries();
        return ResponseEntity.ok(new BaseResponse<List>(entries));
    }

    @PostMapping("/listEntries")
    @ApiOperation(value = "List entries of the topic", notes = "")
    public ResponseEntity<BaseResponse<List>> listEntries(@ApiParam(value = "Topic Id", required = true) @RequestParam(value = "topicId") Long topicId){
        List<Entry> entries= entryService.listEntries(topicId);
        return ResponseEntity.ok(new BaseResponse<List>(entries));
    }

}
