package com.topics.knowledgeBase.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.topics.knowledgeBase.entities.AllView;
import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.entities.TopicView;
import com.topics.knowledgeBase.services.TopicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping(value = "/jsonviews/topics")
public class JsonViewTopicController {

    @Autowired
    TopicService topicService;

    @GetMapping(value = "/external/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(AllView.External.class)
    public TopicView getTopic(@PathVariable("topicId") @Min(1) Long id) {

        Topic topic = topicService.getTopic(id).get();
        TopicView topicView = new TopicView();
        BeanUtils.copyProperties(topic, topicView);
        return  topicView;
    }

    @GetMapping(value = "/internal/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @JsonView(AllView.Internal.class)
    public TopicView getInternalTopic(@PathVariable("topicId") @Min(1) Long id) {

        Topic topic = topicService.getTopic(id).get();
        TopicView topicView = new TopicView();
        BeanUtils.copyProperties(topic, topicView);  // to handle different class as topic class has jsonfilter
        return  topicView;
    }

}
