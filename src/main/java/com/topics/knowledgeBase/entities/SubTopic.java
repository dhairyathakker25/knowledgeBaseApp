package com.topics.knowledgeBase.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
@Builder
public class SubTopic {

    private String topicId;
    private String topicName;
    private String topicDescription;
}
