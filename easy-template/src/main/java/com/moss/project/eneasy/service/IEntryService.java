package com.moss.project.eneasy.service;

import java.util.List;

import com.moss.project.eneasy.model.Entry;

public interface IEntryService {
	void approveEntry(Long objid);
	void cancelEntry(Long objid);
	void addNewEntry(Long topicId, String name);
	Entry readEntry(Long objid);
	List<Entry> readWaitingEntrys();
	List<Entry> readMyEntries();
	void updateEntry(Long objid, String content);
}
