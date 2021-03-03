package com.moss.project.eneasy.service;

import java.util.List;


import com.moss.project.eneasy.enums.TopicStatus;
import com.moss.project.eneasy.model.Topic;

public interface ITopicService {
	List<Topic> readLastTopics();
	void changeTopicStatus(Long objid, TopicStatus status);
	Long addNewTopic(String name, String content);
	Topic readTopic(Long objid);
	List<Topic> listWaitingTopics();
	public List<Topic> readMyTopics();
}
