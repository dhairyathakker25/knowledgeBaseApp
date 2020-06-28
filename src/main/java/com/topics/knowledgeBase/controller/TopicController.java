package com.topics.knowledgeBase.controller;

import com.topics.knowledgeBase.model.Topic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicController {

    @GetMapping(value = "/topiclist")
    public List<Topic> getAllTopics() {
        //hard coded topics
        return Arrays.asList(
                new Topic().setTopicId("topicId1").setTopicName("TopicName1").setTopicDescription("TopicDescription1"),
                new Topic().setTopicId("topicId2").setTopicName("TopicName2").setTopicDescription("TopicDescription2")
        );
    }
}
