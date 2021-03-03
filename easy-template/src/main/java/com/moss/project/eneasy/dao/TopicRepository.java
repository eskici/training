package com.moss.project.eneasy.dao;

import com.moss.project.eneasy.enums.TopicStatus;
import com.moss.project.eneasy.model.Topic;
import com.moss.project.eneasy.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends BaseJpaRepository<Topic, Long> {

	List<Topic> findAllByStatusAndCreatedByOrderByLastChangeDateAsc(TopicStatus statusApprove, UserEntity user);

	List<Topic> findAllByStatusOrderByLastChangeDateAsc(TopicStatus status);

	List<Topic>  findAllByCreatedByAndStatusOrderByLastChangeDate(UserEntity user, TopicStatus status);
}
