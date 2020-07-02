package com.topics.knowledgeBase.exceptions;

import lombok.Getter;
import static java.lang.String.format;

@Getter
public class TopicNameNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 6330081539075842205L;
    private String topicName;

    public TopicNameNotFoundException(String message, String topicName) {
        super(message);
        this.topicName = topicName;
    }
}
