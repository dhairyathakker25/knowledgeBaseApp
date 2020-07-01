package com.topics.knowledgeBase.controller;

import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.exceptions.TopicNameNotUniqueException;
import com.topics.knowledgeBase.exceptions.TopicNotFoundException;
import com.topics.knowledgeBase.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping(value = "/topics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping(value = "/topics/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Topic getTopic(@PathVariable("topicId") Long id) {
        try {
            return topicService.getTopic(id).get();
        } catch(TopicNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PostMapping(value = "/topic", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Topic> addTopic(@RequestBody Topic topic, UriComponentsBuilder builder) {
        try {
            Optional<Topic> topicCreated =  topicService.addTopic(topic);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/topics/{topicId}").buildAndExpand(topicCreated.get().getTopicId()).toUri());
            return new ResponseEntity<Topic>(topicCreated.get(), headers, HttpStatus.CREATED);
        } catch (TopicNameNotUniqueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @PutMapping(value = "/topics/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Topic updateTopic(@PathVariable("topicId") Long id, @RequestBody Topic topic) {
        try {
            return topicService.updateTopic(id, topic).get();
        } catch(TopicNotFoundException | TopicNameNotUniqueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping(value = "/topics/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable("topicId") Long id) {
        try {
            topicService.deleteTopic(id);
        } catch (TopicNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping(value = "/topics/byTopicName/{topicName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Topic getTopic(@PathVariable("topicName") String topicName) {
        return topicService.getTopicByTopicName(topicName);
    }

    @GetMapping(value = "/topics/byTopicDescription/{topicDescription}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Topic> getTopics(@PathVariable("topicDescription") String topicDescription) {
        return topicService.getTopicByTopicDescription(topicDescription);
    }


}
