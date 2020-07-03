package com.topics.knowledgeBase.controller;


import com.topics.knowledgeBase.entities.SubTopic;
import com.topics.knowledgeBase.services.SubTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Validated
@RequestMapping(value = "/topics")
public class SubTopicController {

    @Autowired
    SubTopicService subTopicService;

    @GetMapping(value = "/{topicId}/subTopics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SubTopic> getSubTopicsByTopicId(@PathVariable("topicId") Long topicId) {
        return subTopicService.getSubTopicsByTopicId(topicId).get();
    }

    @PostMapping(value = "/{topicId}/subTopics/subTopic", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubTopic createSubTopicForTopicId(@PathVariable("topicId") Long topicId, @RequestBody SubTopic subTopic) {
        return subTopicService.createSubTopicsByTopicId(topicId, subTopic);
    }
}