package com.topics.knowledgeBase.service;

import com.topics.knowledgeBase.model.Topic;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    List<Topic> topics = Arrays.asList(
            Topic.builder().topicId("topicId1").topicName("TopicName1").topicDescription("TopicDescription1").build(),
            Topic.builder().topicId("topicId2").topicName("TopicName2").topicDescription("TopicDescription2").build(),
            Topic.builder().topicId("topicId3").topicName("TopicName3").topicDescription("TopicDescription3").build()
    );

    public List<Topic> getAllTopics() {
        return topics;
    }

    public Topic getTopic(String topicId) {
        return topics.stream().filter(t -> t.getTopicId().equals(topicId))
                .findFirst()
                .orElse(Topic.builder().topicDescription(null).topicId(null).topicName(null).build());
    }
}
