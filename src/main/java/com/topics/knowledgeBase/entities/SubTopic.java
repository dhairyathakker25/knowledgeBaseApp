package com.topics.knowledgeBase.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Entity
@Table(name="sub_topic_table", uniqueConstraints = @UniqueConstraint(columnNames = {"sub_topic_name", "topic_topic_id"}))
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonFilter(value = "subTopicFilter") // -- commented for jsonviews
//@JsonView(AllView.Internal.class)  // -- comment for json filter
public class SubTopic {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long subTopicId;

    @Column(name = "sub_topic_name", length = 30, nullable = false)
    private String subTopicName;


    @Column(name = "sub_topic_description", length = 50, nullable = false)
    private String subTopicDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topic_topic_id", nullable=false)
    @JsonBackReference   //could use json ignore properties as well but found this one to be better
    private Topic topic;

    @Tolerate
    public SubTopic() {
        // no args constructor is required for JPA which conflicts with lombok Builder
    }

}
