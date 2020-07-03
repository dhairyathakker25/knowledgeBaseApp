package com.topics.knowledgeBase.services;


import com.topics.knowledgeBase.entities.SubTopic;
import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.exceptions.SubTopicNameNotUniqueException;
import com.topics.knowledgeBase.exceptions.TopicIdNotFoundException;
import com.topics.knowledgeBase.repositories.SubTopicRepository;
import com.topics.knowledgeBase.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubTopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubTopicRepository subTopicRepository;

    public Optional<List<SubTopic>> getSubTopicsByTopicId(Long topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if(topic.isPresent())
            return Optional.ofNullable(topic.get().getSubTopics());
        else
            throw new TopicIdNotFoundException("Topic Id Not found", topicId);
    }

    public SubTopic createSubTopicsByTopicId(Long topicId, SubTopic subTopic) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if(topic.isPresent()) {
            try {
                subTopic.setTopic(topic.get());
                return subTopicRepository.saveAndFlush(subTopic);
            } catch(DataIntegrityViolationException ex) {
                throw new SubTopicNameNotUniqueException("SubTopic name not unique", subTopic.getSubTopicName());
            }

        } else {
            throw new TopicIdNotFoundException("Topic Id Not found", topicId);
        }
    }
}
