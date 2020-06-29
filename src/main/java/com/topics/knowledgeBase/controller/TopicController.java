package com.topics.knowledgeBase.controller;

import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping(value = "/topics")
    public ResponseEntity<List<Topic>> getAllTopics() {
        //hard coded topics
        return new ResponseEntity<List<Topic>>(topicService.getAllTopics(), HttpStatus.OK);
    }

    @GetMapping(value = "/topics/{topicId}")
    public ResponseEntity<Object> getTopic(@PathVariable("topicId") long id) {
        if(topicService.getTopic(id).isPresent())
            return new ResponseEntity<Object>(topicService.getTopic(id).get(), HttpStatus.OK);
        else
            return new ResponseEntity<Object>("topic id does not exist", HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/topic")
    public ResponseEntity<Object> addTopic(@RequestBody Topic topic) {
        if(topicService.addTopic(topic)) {
            return new ResponseEntity<Object>(topic, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Object>("Topic not added", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
