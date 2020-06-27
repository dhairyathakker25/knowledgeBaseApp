package com.topics.knowledgeBase.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class SubTopic {

    private String topicId;
    private String topicName;
    private String topicDescription;
}
