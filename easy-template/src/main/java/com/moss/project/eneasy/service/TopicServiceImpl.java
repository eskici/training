package com.moss.project.eneasy.service;

import com.moss.project.eneasy.dao.EntryRepository;
import com.moss.project.eneasy.dao.TopicDAOImpl;
import com.moss.project.eneasy.enums.TopicStatus;
import com.moss.project.eneasy.model.Entry;
import com.moss.project.eneasy.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImpl extends BaseServiceImpl implements ITopicService {

	@Autowired
	private TopicDAOImpl topicDAO;

	@Autowired
	private EntryRepository entryDAO;
	
	public List<Topic> readLastTopics() {
		return topicDAO.readLastTopics();
	}

	public void changeTopicStatus(Long objid, TopicStatus status) {
		Topic topic = topicDAO.readTopic(objid);
		topic.setLastChangeDate(new Date());
		topic.setStatus(status);
		topicDAO.saveOrUpdate(topic);

		topic.getEntries().stream().forEach(e -> e.setStatus(status));
	}

	public Long addNewTopic(String title, String content) {
		Topic topic = new Topic();
		topic.setName(title);
		topic.setStatus(TopicStatus.WAITING);
		topic.setCreatedBy(getCurrentUser());
		Long topicId = topicDAO.saveOrUpdate(topic);
		
		Entry entry = new Entry();
		entry.setContent(content);
		entry.setTopic(topic);
		entry.setStatus(TopicStatus.WAITING);
		entry.setCreatedBy(getCurrentUser());
		entryDAO.save(entry);

		return topicId;
	}

	public Topic readTopic(Long objid) {
		return topicDAO.readTopic(objid);
	}

	public List<Topic> listWaitingTopics() {
		List<Topic> topics = topicDAO.listWaitingTopics();
		topics.stream().forEach(t -> t.getEntries());
		return topics;
	}

	public List<Topic> readMyTopics() {
		return topicDAO.readMyTopics(getCurrentUser());
	}
}
