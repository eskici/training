package com.moss.project.eneasy.repository;

import com.moss.project.eneasy.enums.EnumStatus;
import com.moss.project.eneasy.entity.Entry;
import com.moss.project.eneasy.entity.Topic;
import com.moss.project.eneasy.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends BaseJpaRepository<Entry, Long> {

	List<Entry> findAllByTopicAndStatusOrderById(Topic topic, EnumStatus statusApprove, Pageable pageable);

	List<Entry> findAllByStatusOrderByLastChangeDateAsc(EnumStatus status, Pageable pageable);

	List<Entry>  findAllByCreatedByAndStatusOrderByLastChangeDate(User user, EnumStatus status);
}
