package com.topics.knowledgeBase.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name="topic_table")  //can include schema name for distinguishing same table across schemas
public class Topic {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long topicId;

    @Column(name = "topic_name", length = 20, nullable = false, unique = true)
    private String topicName;

    @Column(name = "topic_description", length = 50, nullable = false)
    private String topicDescription;

    @Tolerate
    public Topic() {
        // no args constructor is required for JPA which conflicts with lombok Builder
    }
}
