package com.topics.knowledgeBase.exceptions;

import lombok.Getter;
import static java.lang.String.format;

@Getter
public class TopicNotFoundException extends Exception {


    private static final long serialVersionUID = 5046409931672439867L;
    private Long topicId;

    public TopicNotFoundException(String message, Long topicId) {
        super(format(message, topicId));
        this.topicId = topicId;
    }
}
