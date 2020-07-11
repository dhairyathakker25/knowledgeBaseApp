package com.topics.knowledgeBase.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonFilter(value = "topicFilter")   //comment for jsonfilter
public class TopicMmDtoV2  extends RepresentationModel {

    private Long topicId;
    private String topicName;
    private String topicDescription;
    private String topicAuthor;

    @JsonManagedReference
    private List<SubTopicMmDtoV2> subTopics;

    @Tolerate
    public TopicMmDtoV2() {
        // no args constructor is required for JPA which conflicts with lombok Builder
    }
}
