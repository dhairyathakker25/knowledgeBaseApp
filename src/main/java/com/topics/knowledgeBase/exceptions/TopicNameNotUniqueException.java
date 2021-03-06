package com.topics.knowledgeBase.exceptions;

import lombok.Getter;
import static java.lang.String.format;

@Getter
public class TopicNameNotUniqueException extends RuntimeException {


    private static final long serialVersionUID = 814065151136888860L;
    private String topicName;

    public TopicNameNotUniqueException(String message, String topicName) {
        super(message);
        this.topicName = topicName;
    }
}
