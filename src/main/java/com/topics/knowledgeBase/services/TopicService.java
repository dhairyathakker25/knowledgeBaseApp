package com.topics.knowledgeBase.services;

import com.topics.knowledgeBase.entities.Topic;
import com.topics.knowledgeBase.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public Topic getTopic(Long topicId) {
        if(topicRepository.findById(topicId).isPresent())
            return topicRepository.findById(topicId).get();
        else
            return null;
    }

    public Topic addTopic(Topic topic) {
        System.out.println("adding topic:"+topic.getTopicId());
        return topicRepository.saveAndFlush(topic);
    }

    public Topic updateTopic(Long topicId, Topic updateTopic) {
        Topic topic = topicRepository.findById(topicId).get();

        topic.setTopicName(updateTopic.getTopicName());
        topic.setTopicDescription(updateTopic.getTopicDescription());

        return topicRepository.saveAndFlush(topic);

    }

    public void deleteTopic(Long topicId) {
        if(topicRepository.findById(topicId).isPresent())
            topicRepository.deleteById(topicId);
    }

}
