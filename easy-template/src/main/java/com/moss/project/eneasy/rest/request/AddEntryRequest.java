package com.moss.project.eneasy.rest.request;

import lombok.Data;

/**
 * @author Taner YILDIRIM
 */
@Data
public class AddEntryRequest {
    private String content;
    private Long topicId;
}
