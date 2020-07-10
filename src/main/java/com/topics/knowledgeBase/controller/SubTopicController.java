package com.topics.knowledgeBase.controller;


import com.topics.knowledgeBase.dtos.SubTopicMmDto;
import com.topics.knowledgeBase.entities.SubTopic;
import com.topics.knowledgeBase.services.SubTopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@Validated
@RequestMapping(value = "/topics")
public class SubTopicController {

    @Autowired
    SubTopicService subTopicService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/{topicId}/subTopics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SubTopicMmDto> getSubTopicsByTopicId(@PathVariable("topicId") Long topicId) {
        return subTopicService.getSubTopicsByTopicId(topicId).get().stream()
                .map(subTopic -> modelMapper.map(subTopic, SubTopicMmDto.class)).collect(Collectors.toList());
    }

    @PostMapping(value = "/{topicId}/subTopics/subTopic", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public SubTopicMmDto createSubTopicForTopicId(@PathVariable("topicId") Long topicId, @RequestBody SubTopicMmDto subTopic) {
        SubTopic subTopicToAdd = modelMapper.map(subTopic, SubTopic.class);
        return modelMapper.map(subTopicService.createSubTopicsByTopicId(topicId, subTopicToAdd), SubTopicMmDto.class);
    }

    @GetMapping(value = "/{topicId}/subTopics/{subTopicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SubTopicMmDto getSubTopicForTopicId(@PathVariable("topicId") Long topicId, @PathVariable("subTopicId") Long subTopicId) {
        return modelMapper.map(subTopicService.getSubTopicBySubTopicId(topicId, subTopicId), SubTopicMmDto.class);
    }

    @PutMapping(value = "/{topicId}/subTopics/{subTopicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SubTopicMmDto updateSubTopicForTopicId(@PathVariable("topicId") Long topicId, @PathVariable("subTopicId") Long subTopicId, @RequestBody SubTopicMmDto subTopic) {
        SubTopic subTopicToUpdate = modelMapper.map(subTopic, SubTopic.class);
        return modelMapper.map(subTopicService.updateSubTopicBySubTopicId(topicId, subTopicId, subTopicToUpdate), SubTopicMmDto.class);
    }

    @PatchMapping(value = "/{topicId}/subTopics/{subTopicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SubTopicMmDto patchSubTopic(@PathVariable("topicId") Long topicId, @PathVariable("subTopicId") Long subTopicId, @RequestBody SubTopicMmDto patchSubTopic) {
        SubTopic subTopicToPatch = modelMapper.map(patchSubTopic, SubTopic.class);
        return modelMapper.map(subTopicService.patchSubTopic(topicId, subTopicId, subTopicToPatch), SubTopicMmDto.class);
    }
}
