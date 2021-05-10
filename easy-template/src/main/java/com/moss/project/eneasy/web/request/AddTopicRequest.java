package com.moss.project.eneasy.web.request;

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
