package com.moss.project.eneasy.dao;

import com.moss.project.eneasy.enums.EnumStatus;
import com.moss.project.eneasy.entity.Topic;
import com.moss.project.eneasy.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends BaseJpaRepository<Topic, Long> {

	List<Topic> findAllByStatusAndCreatedByOrderByLastChangeDateAsc(EnumStatus statusApprove, UserEntity user);

	List<Topic> findAllByStatusOrderByLastChangeDateAsc(EnumStatus status);

	List<Topic>  findAllByCreatedByAndStatusOrderByLastChangeDate(UserEntity user, EnumStatus status);
}
