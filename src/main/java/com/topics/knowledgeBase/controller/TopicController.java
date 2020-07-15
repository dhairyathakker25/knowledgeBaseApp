package com.topics.knowledgeBase.controller;

import com.topics.knowledgeBase.dtos.TopicMmDto;
import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.services.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "Topic Management Controller", value = "TopicController")
@RestController
@Validated
@RequestMapping(value = "/topics/v1")
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ResourceBundleMessageSource messageSource;

    @ApiOperation(value = "get greetings for the app in english and french")
    @GetMapping(value = "/greetings", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAppGreetings(@RequestHeader(name = "Accept-Language", required = false) String locale) {
        if (locale != null)
            return messageSource.getMessage("label.greetings", null, new Locale(locale));
        else
            return messageSource.getMessage("label.greetings", null, new Locale("en"));
    }

    @ApiOperation(value = "get default greetings for the app")
    @GetMapping(value = "/greetings-without-locale", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAppGreetings() {
        return messageSource.getMessage("label.greetings", null, LocaleContextHolder.getLocale());
    }

    @ApiOperation(value = "get List of topics ")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<TopicMmDto> getAllTopics() {
        return topicService.getAllTopics().stream()
                .map(topic -> modelMapper.map(topic, TopicMmDto.class))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "get  topic based on topic id ")
    @GetMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TopicMmDto getTopic(@PathVariable("topicId") @Min(1) Long id) {
        return modelMapper.map(topicService.getTopic(id).get(), TopicMmDto.class);
    }



    @ApiOperation(value = "post or create  topic ")
    @PostMapping(value = "/topic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopicMmDto> addTopic(@ApiParam("topic details to create new topic") @Valid @RequestBody TopicMmDto topic, UriComponentsBuilder builder) {

        Topic topicToAdd = modelMapper.map(topic, Topic.class);
        Optional<Topic> topicCreated = topicService.addTopic(topicToAdd);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/topics/{topicId}").buildAndExpand(topicCreated.get().getTopicId()).toUri());
        return new ResponseEntity<TopicMmDto>(modelMapper.map(topicCreated.get(), TopicMmDto.class), headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "update topic based on id ")
    @PutMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TopicMmDto updateTopic(@ApiParam("topic Id to update topic") @PathVariable("topicId") Long id, @ApiParam("topic body  to update topic") @RequestBody TopicMmDto topic) {
        Topic topicToUpdate = modelMapper.map(topic, Topic.class);
        return modelMapper.map(topicService.patchTopic(id, topicToUpdate).get(), TopicMmDto.class);
    }


    @ApiOperation(value = "patch topic based on id ")
    @PatchMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TopicMmDto patchTopic(@PathVariable("topicId") Long id, @RequestBody TopicMmDto topic) {
        Topic topicToUpdate = modelMapper.map(topic, Topic.class);
        return modelMapper.map(topicService.patchTopic(id, topicToUpdate).get(), TopicMmDto.class);
    }

    @ApiOperation(value = "delete topic based on id ")
    @DeleteMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable("topicId") Long id) {
        topicService.deleteTopic(id);
    }

    @ApiOperation(value = "get topic based on topic name ")
    @GetMapping(value = "/byTopicName/{topicName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TopicMmDto getTopic(@PathVariable("topicName") String topicName) {
        return modelMapper.map(topicService.getTopicByTopicName(topicName), TopicMmDto.class);
    }

    @ApiOperation(value = "get topic based on topic description ")
    @GetMapping(value = "/byTopicDescription/{topicDescription}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<TopicMmDto> getTopics(@PathVariable("topicDescription") String topicDescription) {
        List<Topic> topics = topicService.getTopicByTopicDescription(topicDescription);
        return topics.stream().map(topic -> modelMapper.map(topic, TopicMmDto.class)).collect(Collectors.toList());
    }

}
