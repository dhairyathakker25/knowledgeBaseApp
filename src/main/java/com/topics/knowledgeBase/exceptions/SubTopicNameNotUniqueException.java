package com.topics.knowledgeBase.exceptions;

import lombok.Getter;

@Getter
public class SubTopicNameNotUniqueException  extends RuntimeException{

    private static final long serialVersionUID = 6699934249422588044L;

    private String subTopicName;

    public SubTopicNameNotUniqueException(String message, String subTopicName) {
        super(message);
        this.subTopicName = subTopicName;
    }
}
