package com.topics.knowledgeBase.controller;


import com.topics.knowledgeBase.entities.SubTopic;
import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.repositories.TopicRepository;
import com.topics.knowledgeBase.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(value = "/hateoas/topics")
public class TopicHateoasController {


    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<Topic> getAllTopics() {
         List<Topic> topicList= topicService.getAllTopics();
         for(Topic topic: topicList) {
             topic.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash(topic.getTopicId()).withSelfRel());

             CollectionModel<SubTopic> subtopics = WebMvcLinkBuilder.methodOn(SubTopicHateoasController.class)
                     .getSubTopicsByTopicId(topic.getTopicId());

             Link subTopicLink = WebMvcLinkBuilder.linkTo(subtopics).withRel("all-sub-topics");
             topic.add(subTopicLink);
         }

         //generating self link for get all topics
         Link getAllTopicsSelfLink = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();

         return new CollectionModel<Topic>(topicList, getAllTopicsSelfLink);
    }

    @GetMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Topic> getTopic(@PathVariable("topicId") @Min(1) Long id) {
        Topic topic = topicService.getTopic(id).get();
        Long topicId = topic.getTopicId();
        Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(topicId).withSelfRel();
        topic.add(selfLink);
        return new EntityModel<Topic>(topic);

    }

}
