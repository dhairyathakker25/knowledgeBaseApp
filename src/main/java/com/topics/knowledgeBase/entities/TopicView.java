package com.topics.knowledgeBase.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@Table(name="topic_table")  //can include schema name for distinguishing same table across schemas
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicView {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonView(AllView.External.class)
    private Long topicId;

    @NotEmpty(message = "Topic Name cannot be empty")
    @Size(min=2, message = "Size must be atleast 2 characters")
    @Size(max=20, message = "Size cannot be more than 20 characters")
    @Column(name = "topic_name", length = 20, nullable = false, unique = true)
    @JsonView(AllView.External.class)
    private String topicName;

    @Column(name = "topic_description", length = 50, nullable = false)
    @JsonView(AllView.Internal.class)
    private String topicDescription;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonView(AllView.Internal.class)
    private List<SubTopic> subTopics;

    @Tolerate
    public TopicView() {
        // no args constructor is required for JPA which conflicts with lombok Builder
    }
}
