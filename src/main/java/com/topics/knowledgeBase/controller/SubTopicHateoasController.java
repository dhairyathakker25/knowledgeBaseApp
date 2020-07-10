package com.topics.knowledgeBase.controller;


import com.topics.knowledgeBase.dtos.SubTopicMmDto;
import com.topics.knowledgeBase.entities.SubTopic;
import com.topics.knowledgeBase.services.SubTopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping(value = "/hateoas/topics")
public class SubTopicHateoasController {

    @Autowired
    SubTopicService subTopicService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/{topicId}/subTopics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<SubTopicMmDto> getSubTopicsByTopicId(@PathVariable("topicId") Long topicId) {
        List<SubTopic> subTopicList = subTopicService.getSubTopicsByTopicId(topicId).get();
        List<SubTopicMmDto> subTopicMmDtoList = subTopicList.stream().map(subTopic -> modelMapper.map(subTopic, SubTopicMmDto.class)).collect(Collectors.toList());
        return new CollectionModel<SubTopicMmDto>(subTopicMmDtoList);
    }
}
