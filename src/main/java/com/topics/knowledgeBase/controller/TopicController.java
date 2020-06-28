package com.topics.knowledgeBase.controller;

import com.topics.knowledgeBase.model.Topic;
import com.topics.knowledgeBase.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping(value = "/topics")
    public List<Topic> getAllTopics() {
        //hard coded topics
        return topicService.getAllTopics();

    }

    @GetMapping(value = "/topics/{topicId}")
    public Topic getTopic(@PathVariable("topicId") String id) {
        return topicService.getTopic(id);
    }
}
