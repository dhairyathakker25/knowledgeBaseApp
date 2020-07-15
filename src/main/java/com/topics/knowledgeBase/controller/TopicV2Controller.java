package com.topics.knowledgeBase.controller;

import com.topics.knowledgeBase.dtos.TopicMmDtoV2;
import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.services.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping(value = "/topics/v2")
public class TopicV2Controller {

    @Autowired
    TopicService topicService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ResourceBundleMessageSource messageSource;

    @GetMapping(value = "/greetings", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAppGreetings(@RequestHeader(name = "Accept-Language", required = false) String locale) {
        if (locale != null)
            return messageSource.getMessage("label.greetings", null, new Locale(locale));
        else
            return messageSource.getMessage("label.greetings", null, new Locale("en"));
    }

    @GetMapping(value = "/greetings-without-locale", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String getAppGreetings() {
        return messageSource.getMessage("label.greetings", null, LocaleContextHolder.getLocale());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<TopicMmDtoV2> getAllTopics() {
        return topicService.getAllTopics().stream()
                .map(topic -> modelMapper.map(topic, TopicMmDtoV2.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TopicMmDtoV2 getTopic(@PathVariable("topicId") @Min(1) Long id) {
        return modelMapper.map(topicService.getTopic(id).get(), TopicMmDtoV2.class);
    }

    @PostMapping(value = "/topic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopicMmDtoV2> addTopic(@Valid @RequestBody TopicMmDtoV2 topic, UriComponentsBuilder builder) {

        Topic topicToAdd = modelMapper.map(topic, Topic.class);
        Optional<Topic> topicCreated = topicService.addTopic(topicToAdd);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/topics/{topicId}").buildAndExpand(topicCreated.get().getTopicId()).toUri());
        return new ResponseEntity<TopicMmDtoV2>(modelMapper.map(topicCreated.get(), TopicMmDtoV2.class), headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TopicMmDtoV2 updateTopic(@PathVariable("topicId") Long id, @RequestBody TopicMmDtoV2 topic) {
        Topic topicToUpdate = modelMapper.map(topic, Topic.class);
        return modelMapper.map(topicService.patchTopic(id, topicToUpdate).get(), TopicMmDtoV2.class);
    }

    @PatchMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TopicMmDtoV2 patchTopic(@PathVariable("topicId") Long id, @RequestBody TopicMmDtoV2 topic) {
        Topic topicToUpdate = modelMapper.map(topic, Topic.class);
        return modelMapper.map(topicService.patchTopic(id, topicToUpdate).get(), TopicMmDtoV2.class);
    }

    @DeleteMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable("topicId") Long id) {
        topicService.deleteTopic(id);
    }

    @GetMapping(value = "/byTopicName/{topicName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TopicMmDtoV2 getTopic(@PathVariable("topicName") String topicName) {
        return modelMapper.map(topicService.getTopicByTopicName(topicName), TopicMmDtoV2.class);
    }

    @GetMapping(value = "/byTopicDescription/{topicDescription}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<TopicMmDtoV2> getTopics(@PathVariable("topicDescription") String topicDescription) {
        List<Topic> topics = topicService.getTopicByTopicDescription(topicDescription);
        return topics.stream().map(topic -> modelMapper.map(topic, TopicMmDtoV2.class)).collect(Collectors.toList());
    }
}
