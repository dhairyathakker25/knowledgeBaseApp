package com.topics.knowledgeBase.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.exceptions.SubTopicNameNotUniqueException;
import com.topics.knowledgeBase.exceptions.TopicNameNotFoundException;
import com.topics.knowledgeBase.exceptions.TopicNameNotUniqueException;
import com.topics.knowledgeBase.exceptions.TopicIdNotFoundException;
import com.topics.knowledgeBase.repositories.TopicRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    ObjectMapper objectMapper;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Optional<Topic> getTopic(Long topicId) throws TopicIdNotFoundException {

        Optional<Topic> topic = topicRepository.findById(topicId);

        if(topic.isPresent())
            return topic;
        else
            throw new TopicIdNotFoundException("Topic Id not found", topicId);
    }

    @Transactional
    public Optional<Topic> addTopic(Topic topic) {
        if(!topicRepository.findOneByTopicName(topic.getTopicName()).isPresent())
            try{
                return Optional.of(topicRepository.saveAndFlush(topic));
            } catch(DataIntegrityViolationException ex) {
                throw new SubTopicNameNotUniqueException("sub Topic name not unique", ex.getMessage());
            }

        else
            throw new TopicNameNotUniqueException("Topic name exists", topic.getTopicName());

    }

    public Optional<Topic> updateTopic(Long topicId, Topic updateTopic)  {
        Optional<Topic> topic = topicRepository.findById(topicId);

        if(topic.isPresent()) {
            try {
                topic.get().setTopicName(updateTopic.getTopicName());
                topic.get().setTopicDescription(updateTopic.getTopicDescription());
                return Optional.of(topicRepository.saveAndFlush(topic.get()));
            } catch(DataIntegrityViolationException e) {
                throw new TopicNameNotUniqueException("Topic name exists", updateTopic.getTopicName());
            }

        } else
            throw new TopicIdNotFoundException("Topic Id not found for update", topicId);
    }


    public Optional<Topic> patchTopic(Long topicId, Topic patchTopic) {

        Optional<Topic> topic = topicRepository.findById(topicId);

        if(topic.isPresent()) {
            try {
                Map<String, Object> patchFields = objectMapper.convertValue(patchTopic, new TypeReference<Map<String, Object>>() {});

                patchFields.forEach((k, v) -> {
                    // use reflection to get field k on manager and set it to value v
                    Field field = ReflectionUtils.findField(Topic.class, k);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, topic.get(), v);
                });
                return Optional.of(topicRepository.saveAndFlush(topic.get()));
            } catch(DataIntegrityViolationException e) {
                throw new TopicNameNotUniqueException("Topic name exists", patchTopic.getTopicName());
            }

        } else
            throw new TopicIdNotFoundException("Topic Id not found for update", topicId);


    }

    public void deleteTopic(Long topicId) {
        if(topicRepository.findById(topicId).isPresent())
            topicRepository.deleteById(topicId);
        else
            throw new TopicIdNotFoundException("Topic Id not found for delete", topicId);
    }

    public Topic getTopicByTopicName(String topicName) {

        Optional<Topic> topic = topicRepository.findOneByTopicName(topicName);
        if(topic.isPresent())
            return topic.get();
        else
            throw new TopicNameNotFoundException("Topic name does not exist", topicName);
    }

    public List<Topic> getTopicByTopicDescription(String topicDescription) {
        return topicRepository.findAllByTopicDescription(topicDescription);
    }


}
