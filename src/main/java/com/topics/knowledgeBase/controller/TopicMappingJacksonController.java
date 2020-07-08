package com.topics.knowledgeBase.controller;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.topics.knowledgeBase.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Set;

@RestController
@Validated
@RequestMapping(value = "/jacksonfilters/topics")
public class TopicMappingJacksonController {

    @Autowired
    TopicService topicService;

    @GetMapping(value = "/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MappingJacksonValue getTopic(@PathVariable("topicId") @Min(1) Long id) {

        FilterProvider filter = new SimpleFilterProvider()
                .addFilter("topicFilter", SimpleBeanPropertyFilter.filterOutAllExcept("topicName", "topicDescription", "subTopics"))
                .addFilter("subTopicFilter", SimpleBeanPropertyFilter.filterOutAllExcept("subTopicName", "subTopicId"));

        MappingJacksonValue mapper = new MappingJacksonValue(topicService.getTopic(id).get());
        mapper.setFilters(filter);

        return mapper;
    }

    @GetMapping(value = "/params/{topicId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MappingJacksonValue getTopicByRequestParams(@PathVariable("topicId") @Min(1) Long id,
                                                       @RequestParam("topicFields") String[] topicFields,
                                                       @RequestParam("subTopicFields") String[] subTopicFields) {

        FilterProvider filter = new SimpleFilterProvider()
                .addFilter("topicFilter", SimpleBeanPropertyFilter.filterOutAllExcept(topicFields))
                .addFilter("subTopicFilter", SimpleBeanPropertyFilter.filterOutAllExcept(subTopicFields));

        MappingJacksonValue mapper = new MappingJacksonValue(topicService.getTopic(id).get());
        mapper.setFilters(filter);

        return mapper;
    }

}
