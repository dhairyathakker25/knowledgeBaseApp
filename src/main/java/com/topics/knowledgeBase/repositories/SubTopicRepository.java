package com.topics.knowledgeBase.repositories;


import com.topics.knowledgeBase.entities.SubTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTopicRepository extends JpaRepository<SubTopic, Long> {
}
