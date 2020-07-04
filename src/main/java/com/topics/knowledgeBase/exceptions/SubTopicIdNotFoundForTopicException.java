package com.topics.knowledgeBase.exceptions;

import lombok.Getter;

@Getter
public class SubTopicIdNotFoundForTopicException extends RuntimeException{

    private static final long serialVersionUID = 5543487808277262562L;
    private Long subTopicId;

    public SubTopicIdNotFoundForTopicException(String message, Long subTopicId) {
        super(message);
        this.subTopicId = subTopicId;
    }
}
