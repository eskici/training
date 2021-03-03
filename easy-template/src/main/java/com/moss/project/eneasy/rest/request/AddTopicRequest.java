package com.moss.project.eneasy.rest.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Taner YILDIRIM
 */
@Getter
@Setter
public class AddTopicRequest {
    private String title;
    private String content;
}
