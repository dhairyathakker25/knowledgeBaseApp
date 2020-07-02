package com.topics.knowledgeBase.exceptions;

import lombok.Getter;
import static java.lang.String.format;

@Getter
public class TopicIdNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 5046409931672439867L;
    private Long topicId;

    public TopicIdNotFoundException(String message, Long topicId) {
        super(message);
        this.topicId = topicId;
    }
}
