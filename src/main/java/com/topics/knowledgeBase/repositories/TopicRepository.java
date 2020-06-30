package com.topics.knowledgeBase.repositories;

import com.topics.knowledgeBase.entities.Topic;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findOneByTopicName(String topicName);
    List<Topic> findAllByTopicDescription(String topicDescription);
}
