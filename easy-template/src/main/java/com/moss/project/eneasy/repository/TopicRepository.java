package com.moss.project.eneasy.repository;

import com.moss.project.eneasy.enums.EnumStatus;
import com.moss.project.eneasy.entity.Topic;
import com.moss.project.eneasy.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends BaseJpaRepository<Topic, Long> {

	List<Topic> findAllByStatusAndCreatedByOrderByLastChangeDateAsc(EnumStatus statusApprove, User user);

	List<Topic> findAllByStatusOrderByLastChangeDateAsc(EnumStatus status);

	List<Topic>  findAllByCreatedByAndStatusOrderByLastChangeDate(User user, EnumStatus status);
}
