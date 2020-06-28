package com.topics.knowledgeBase.service;

import com.topics.knowledgeBase.model.Topic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    List<Topic> topics = new ArrayList<>(Arrays.asList(
            Topic.builder().topicId(1).topicName("TopicName1").topicDescription("TopicDescription1").build(),
            Topic.builder().topicId(2).topicName("TopicName2").topicDescription("TopicDescription2").build(),
            Topic.builder().topicId(3).topicName("TopicName3").topicDescription("TopicDescription3").build()
    ));

    public List<Topic> getAllTopics() {
        return topics;
    }

    public Optional<Topic> getTopic(long topicId) {
        return topics.stream().filter(t -> t.getTopicId() == topicId)
                .findFirst();
    }

    public boolean addTopic(Topic topic) {
        System.out.println("adding topic:"+topic.getTopicId());
        return topics.add(topic);
    }
}
