package com.topics.knowledgeBase.controller;

import com.topics.knowledgeBase.model.Topic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicController {

    @GetMapping(value = "/topics")
    public List<Topic> getAllTopics() {
        //hard coded topics
        return Arrays.asList(
                Topic.builder().topicId("topicId1").topicName("TopicName1").topicDescription("TopicDescription1").build(),
                Topic.builder().topicId("topicId2").topicName("TopicName2").topicDescription("TopicDescription2").build()
        );
    }
}
