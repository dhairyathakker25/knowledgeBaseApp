package com.topics.knowledgeBase.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.topics.knowledgeBase.entities.Topic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonFilter(value = "subTopicFilter") // -- commented for jsonviews
//@JsonView(AllView.Internal.class)  // -- comment for json filter
public class SubTopicMmDto extends RepresentationModel{

    @ApiModelProperty(notes = "topic auto generated", required = true, position = 1)
    private Long subTopicId;

    @ApiModelProperty(notes = "sub topic name to be provided", required = true, position = 2)
    @Size(max=50, message = "Size cannot be more than 30 characters")
    private String subTopicName;

    @ApiModelProperty(notes = "sub topic desctiption to be provided", required = true, position = 3)
    @Size(max=50, message = "Size cannot be more than 50 characters")
    private String subTopicDescription;

    @JsonBackReference
    private TopicMmDto topic;

    @Tolerate
    public SubTopicMmDto() {
        // no args constructor is required for JPA which conflicts with lombok Builder
    }

}
