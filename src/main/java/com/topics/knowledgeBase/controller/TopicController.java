package com.topics.knowledgeBase.controller;

import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;

@RestController
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping(value = "/topics")
    public ResponseEntity<List<Topic>> getAllTopics() {
        return new ResponseEntity<List<Topic>>(topicService.getAllTopics(), HttpStatus.OK);
    }

    @GetMapping(value = "/topics/{topicId}")
    public ResponseEntity<Object> getTopic(@PathVariable("topicId") Long id) {
        return new ResponseEntity<Object>(topicService.getTopic(id), HttpStatus.OK);
    }

    @PostMapping(value = "/topic")
    public ResponseEntity<Object> addTopic(@RequestBody Topic topic) {
        Topic createdTopic = topicService.addTopic(topic);
        return new ResponseEntity<Object>(createdTopic, HttpStatus.CREATED);
    }

    @PutMapping(value = "/topics/{topicId}")
    public ResponseEntity<Object> updateTopic(@PathVariable("topicId") Long id, @RequestBody Topic topic) {
        Topic updatedTopic = topicService.updateTopic(id, topic);
        return new ResponseEntity<Object>(updatedTopic, HttpStatus.OK);
    }

    @DeleteMapping(value = "/topics/{topicId}")
    public ResponseEntity deleteTopic(@PathVariable("topicId") Long id) {
        topicService.deleteTopic(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/topics/byTopicName/{topicName}")
    public ResponseEntity<Object> getTopic(@PathVariable("topicName") String topicName) {
        return new ResponseEntity<Object>(topicService.getTopicByTopicName(topicName), HttpStatus.OK);
    }

    @GetMapping(value = "/topics/byTopicDescription/{topicDescription}")
    public ResponseEntity<Object> getTopics(@PathVariable("topicDescription") String topicDescription) {
        return new ResponseEntity<Object>(topicService.getTopicByTopicDescription(topicDescription), HttpStatus.OK);
    }


}
