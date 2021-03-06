package com.topics.knowledgeBase.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name="topic_table")  //can include schema name for distinguishing same table across schemas
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonFilter(value = "topicFilter")   //comment for jsonfilter
public class Topic {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long topicId;


    @Column(name = "topic_name", length = 20, nullable = false, unique = true)
    private String topicName;


    @Column(name = "topic_description", length = 50, nullable = false)
    private String topicDescription;


    @Column(name = "topic_author", length = 50)
    private String topicAuthor;

    @OneToMany(orphanRemoval = true, mappedBy = "topic", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SubTopic> subTopics;

    @Tolerate
    public Topic() {
        // no args constructor is required for JPA which conflicts with lombok Builder
    }
}
