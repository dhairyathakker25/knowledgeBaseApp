package com.topics.knowledgeBase.controller;


import com.topics.knowledgeBase.dtos.SubTopicMmDto;
import com.topics.knowledgeBase.dtos.TopicMmDto;
import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.entities.SubTopic;
import com.topics.knowledgeBase.services.TopicService;
import org.modelmapper.ModelMapper;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping(value = "/hateoas/topics")
public class TopicHateoasController {

    @Autowired
    private TopicService topicService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TopicMmDto> getAllTopics() {
         List<TopicMmDto> topicList= topicService.getAllTopics().stream().map(topic -> modelMapper.map(topic, TopicMmDto.class)).collect(Collectors.toList());

         for(TopicMmDto topic: topicList) {
             topic.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash(topic.getTopicId()).withSelfRel());

             CollectionModel<SubTopicMmDto> subtopics = WebMvcLinkBuilder.methodOn(SubTopicHateoasController.class)
                     .getSubTopicsByTopicId(topic.getTopicId());

             Link subTopicLink = WebMvcLinkBuilder.linkTo(subtopics).withRel("all-sub-topics");
             topic.add(subTopicLink);
         }

         //generating self link for get all topics
         Link getAllTopicsSelfLink = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();

         return new CollectionModel<TopicMmDto>(topicList, getAllTopicsSelfLink);
    }

    @GetMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TopicMmDto> getTopic(@PathVariable("topicId") @Min(1) Long id) {
        TopicMmDto topic = modelMapper.map(topicService.getTopic(id).get(), TopicMmDto.class);
        Long topicId = topic.getTopicId();
        Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(topicId).withSelfRel();
        topic.add(selfLink);
        return new EntityModel<TopicMmDto>(topic);

    }

}
