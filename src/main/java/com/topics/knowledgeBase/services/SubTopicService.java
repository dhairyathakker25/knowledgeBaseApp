package com.topics.knowledgeBase.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.topics.knowledgeBase.dtos.SubTopicMmDto;
import com.topics.knowledgeBase.dtos.TopicMmDto;
import com.topics.knowledgeBase.entities.SubTopic;
import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.exceptions.SubTopicIdNotFoundForTopicException;
import com.topics.knowledgeBase.exceptions.SubTopicNameNotUniqueException;
import com.topics.knowledgeBase.exceptions.TopicIdNotFoundException;
import com.topics.knowledgeBase.repositories.SubTopicRepository;
import com.topics.knowledgeBase.repositories.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubTopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubTopicRepository subTopicRepository;

    @Autowired
    ObjectMapper objectMapper;

    public Optional<List<SubTopic>> getSubTopicsByTopicId(Long topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (topic.isPresent())
            return Optional.of(topic.get().getSubTopics());
        else
            throw new TopicIdNotFoundException("Topic Id Not found", topicId);
    }

    public SubTopic createSubTopicsByTopicId(Long topicId, SubTopic subTopic) {
        Optional<Topic> topic = topicRepository.findById(topicId);

        if (topic.isPresent()) {
            try {
                subTopic.setTopic(topic.get());
                return subTopicRepository.saveAndFlush(subTopic);
            } catch (DataIntegrityViolationException ex) {
                throw new SubTopicNameNotUniqueException("SubTopic name not unique", subTopic.getSubTopicName());
            }

        } else {
            throw new TopicIdNotFoundException("Topic Id Not found", topicId);
        }
    }

    public SubTopic updateSubTopicBySubTopicId(Long topicId, Long subTopicId, SubTopic subTopic) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (topic.isPresent()) {
            Optional<SubTopic> subTopicToUpdate = topic.get().getSubTopics().stream()
                    .filter(c -> c.getSubTopicId() == subTopicId).findFirst();

            if (subTopicToUpdate.isPresent()) {
                try {
                    subTopic.setTopic(topic.get());
                    subTopic.setSubTopicId(subTopicId);
                    return subTopicRepository.saveAndFlush(subTopic);
                } catch (DataIntegrityViolationException ex) {
                    throw new SubTopicNameNotUniqueException("SubTopic name not unique", subTopic.getSubTopicName());
                }
            } else {
                throw new SubTopicIdNotFoundForTopicException("Sub Topic Id Not found", subTopicId);
            }

        } else {
            throw new TopicIdNotFoundException("Topic Id Not found", topicId);
        }
    }

    public SubTopic patchSubTopic(Long topicId, Long subTopicId, SubTopic patchSubTopic) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (topic.isPresent()) {
            Optional<SubTopic> subTopicToPatch = topic.get().getSubTopics().stream()
                    .filter(c -> c.getSubTopicId() == subTopicId).findFirst();

            if (subTopicToPatch.isPresent()) {
                try {
                    Map<String, Object> patchFields = objectMapper.convertValue(patchSubTopic, new TypeReference<Map<String, Object>>() {
                    });

                    patchFields.forEach((k, v) -> {
                        // use reflection to get field k on manager and set it to value v
                        Field field = ReflectionUtils.findField(SubTopic.class, k);
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, subTopicToPatch.get(), v);
                    });
                    return subTopicRepository.saveAndFlush(subTopicToPatch.get());

                } catch (DataIntegrityViolationException ex) {
                    throw new SubTopicNameNotUniqueException("SubTopic name not unique", patchSubTopic.getSubTopicName());
                }
            } else {
                throw new SubTopicIdNotFoundForTopicException("Sub Topic Id Not found", subTopicId);
            }

        } else {
            throw new TopicIdNotFoundException("Topic Id Not found", topicId);
        }
    }

    public SubTopic getSubTopicBySubTopicId(Long topicId, Long subTopicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if (topic.isPresent()) {

            Optional<SubTopic> subTopic = topic.get().getSubTopics().stream().filter(c -> c.getSubTopicId() == subTopicId).findFirst();

            if (subTopic.isPresent())
                return subTopic.get();
            else
                throw new SubTopicIdNotFoundForTopicException("subtopic not found for topic:" + topicId, subTopicId);

        } else {
            throw new TopicIdNotFoundException("Topic Id Not found", topicId);
        }

    }
}
