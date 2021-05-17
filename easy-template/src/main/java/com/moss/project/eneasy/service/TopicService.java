package com.moss.project.eneasy.service;

import com.moss.project.eneasy.repository.EntryRepository;
import com.moss.project.eneasy.repository.TopicRepository;
import com.moss.project.eneasy.enums.EnumStatus;
import com.moss.project.eneasy.entity.Entry;
import com.moss.project.eneasy.entity.Topic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class TopicService extends BaseService  {

	private EntryRepository entryRepository;

	private TopicRepository topicRepository;
	
	public List<Topic> readLastTopics() {
		return topicRepository.findAllByStatusOrderByLastChangeDateAsc(EnumStatus.APPROVED);
	}

	public void updateTopicStatus(Long objid, EnumStatus status) {
		Topic topic = readTopic(objid);
		topic.setLastChangeDate(new Date());
		topic.setStatus(status);
		topic.getEntries().stream().forEach(e -> e.setStatus(status));
		topicRepository.save(topic);
	}

	public Long addTopic(String title, String content) {
		Topic topic = new Topic();
		topic.setName(title);
		topic.setStatus(EnumStatus.WAITING);
		topic.setCreatedBy(getCurrentUser());
		topic = topicRepository.save(topic);
		
		Entry entry = new Entry();
		entry.setContent(content);
		entry.setTopic(topic);
		entry.setStatus(EnumStatus.WAITING);
		entry.setCreatedBy(getCurrentUser());
		entryRepository.save(entry);

		return topic.getId();
	}

	private Topic readTopic(Long objid) {
		return topicRepository.findById(objid).orElse(null);
	}

	public List<Topic> listWaitingTopics() {
		return topicRepository.findAllByStatusOrderByLastChangeDateAsc(EnumStatus.WAITING);
	}

	public List<Topic> readMyTopics() {
		return topicRepository.findAllByStatusAndCreatedByOrderByLastChangeDateAsc(EnumStatus.APPROVED, getCurrentUser());
	}
}
