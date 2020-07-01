package com.topics.knowledgeBase.services;

import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.exceptions.TopicNameNotUniqueException;
import com.topics.knowledgeBase.exceptions.TopicNotFoundException;
import com.topics.knowledgeBase.repositories.TopicRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Optional<Topic> getTopic(Long topicId) throws TopicNotFoundException {

        Optional<Topic> topic = topicRepository.findById(topicId);

        if(topic.isPresent())
            return topic;
        else
            throw new TopicNotFoundException("Topic not found", topicId);
    }

    public Optional<Topic> addTopic(Topic topic) throws TopicNameNotUniqueException {
        if(topicRepository.findOneByTopicName(topic.getTopicName()) == null)
            return Optional.of(topicRepository.saveAndFlush(topic));
        else
            throw new TopicNameNotUniqueException("Topic name exists: %s", topic.getTopicName());

    }

    public Optional<Topic> updateTopic(Long topicId, Topic updateTopic) throws TopicNotFoundException, TopicNameNotUniqueException {
        Optional<Topic> topic = topicRepository.findById(topicId);

        if(topic.isPresent()) {
            try {
                topic.get().setTopicName(updateTopic.getTopicName());
                topic.get().setTopicDescription(updateTopic.getTopicDescription());
                return Optional.of(topicRepository.saveAndFlush(topic.get()));
            } catch(DataIntegrityViolationException e) {
                throw new TopicNameNotUniqueException("Topic name exists: %s", updateTopic.getTopicName());
            }

        } else
            throw new TopicNotFoundException("Topic not found for update: %s", topicId);


    }

    public void deleteTopic(Long topicId) throws TopicNotFoundException {
        if(topicRepository.findById(topicId).isPresent())
            topicRepository.deleteById(topicId);
        else
            throw new TopicNotFoundException("Topic not found for delete: %s", topicId);

    }

    public Topic getTopicByTopicName(String topicName) {
        return topicRepository.findOneByTopicName(topicName);
    }

    public List<Topic> getTopicByTopicDescription(String topicDescription) {
        return topicRepository.findAllByTopicDescription(topicDescription);
    }

}
