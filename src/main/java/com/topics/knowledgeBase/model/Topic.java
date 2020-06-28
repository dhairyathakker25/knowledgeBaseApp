package com.topics.knowledgeBase.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Getter
@Setter
@Accessors(chain = true)
public class Topic {

    private String topicId;
    private String topicName;
    private String topicDescription;
}
