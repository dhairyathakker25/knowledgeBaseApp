package com.topics.knowledgeBase.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "this model is to create topic")
//@JsonFilter(value = "topicFilter")   //comment for jsonfilter
public class TopicMmDto  extends RepresentationModel {

    @ApiModelProperty(notes = "auto generated topic id", required = true, position = 1)
    private Long topicId;

    @ApiModelProperty(notes = "topic name to be provided", required = true, position = 2)
    @Size(max=20, min=2, message = "Size cannot be more than 20 characters")
    private String topicName;

    @ApiModelProperty(notes = "topic description to be provided", required = true, position = 3)
    @Size(max=50, message = "Size cannot be more than 50 characters")
    private String topicDescription;

    @ApiModelProperty(notes = "list of subtopics", position = 4)
    @JsonManagedReference
    private List<SubTopicMmDto> subTopics;

    @Tolerate
    public TopicMmDto() {
        // no args constructor is required for JPA which conflicts with lombok Builder
    }
}
