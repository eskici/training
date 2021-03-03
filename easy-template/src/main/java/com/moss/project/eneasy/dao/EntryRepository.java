package com.moss.project.eneasy.dao;

import com.moss.project.eneasy.enums.TopicStatus;
import com.moss.project.eneasy.model.Entry;
import com.moss.project.eneasy.model.Topic;
import com.moss.project.eneasy.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends BaseJpaRepository<Entry, Long> {

	List<Entry> findAllByTopicAndStatusOrderByLastChangeDateAsc(Topic topic, TopicStatus statusApprove);

	List<Entry> findAllByTopicOrderByLastChangeDate(Topic topic);

	List<Entry> findAllByStatusOrderByLastChangeDateAsc(TopicStatus status);

	List<Entry>  findAllByCreatedByAndStatusOrderByLastChangeDate(UserEntity user, TopicStatus status);
}
