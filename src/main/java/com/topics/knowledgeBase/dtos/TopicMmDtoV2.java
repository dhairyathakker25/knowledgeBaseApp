package com.topics.knowledgeBase.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
//@JsonFilter(value = "topicFilter")   //comment for jsonfilter
public class TopicMmDtoV2  extends RepresentationModel {

    private Long topicId;

    @Size(max=20, min=2, message = "Size cannot be more than 20 characters")
    private String topicName;

    @Size(max=50, message = "Size cannot be more than 50 characters")
    private String topicDescription;

    @Size(max=50, message = "Size cannot be more than 50 characters")
    private String topicAuthor;

    @JsonManagedReference
    private List<SubTopicMmDtoV2> subTopics;

    @Tolerate
    public TopicMmDtoV2() {
        // no args constructor is required for JPA which conflicts with lombok Builder
    }
}
