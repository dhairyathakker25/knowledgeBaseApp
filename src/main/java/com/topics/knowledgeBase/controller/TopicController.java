package com.topics.knowledgeBase.controller;

import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.exceptions.TopicNameNotUniqueException;
import com.topics.knowledgeBase.exceptions.TopicIdNotFoundException;
import com.topics.knowledgeBase.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
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
    public Topic getTopic(@PathVariable("topicId") @Min(1) Long id) {
            return topicService.getTopic(id).get();
    }

    @PostMapping(value = "/topic", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Topic> addTopic(@Valid  @RequestBody Topic topic, UriComponentsBuilder builder) {

            Optional<Topic> topicCreated =  topicService.addTopic(topic);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/topics/{topicId}").buildAndExpand(topicCreated.get().getTopicId()).toUri());
            return new ResponseEntity<Topic>(topicCreated.get(), headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/topics/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Topic updateTopic(@PathVariable("topicId") Long id, @RequestBody Topic topic) {
            return topicService.updateTopic(id, topic).get();
    }

    @DeleteMapping(value = "/topics/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable("topicId") Long id) {
            topicService.deleteTopic(id);
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
