package com.moss.project.eneasy.service;

import com.moss.project.eneasy.dao.EntryDAOImpl;
import com.moss.project.eneasy.dao.TopicDAOImpl;
import com.moss.project.eneasy.enums.TopicStatus;
import com.moss.project.eneasy.model.Entry;
import com.moss.project.eneasy.model.Topic;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EntryServiceImpl extends BaseServiceImpl implements IEntryService {

    private EntryDAOImpl entryDAO;

    private TopicDAOImpl topicDAO;

    @Transactional
    public void addNewEntry(Long topicId, String content) {

        Topic topic = topicDAO.readTopic(topicId);
        topic.setLastChangeDate(new Date());

        Entry entry = new Entry();
        entry.setContent(content);
        entry.setTopic(topic);
        entry.setStatus(TopicStatus.WAITING);
        entry.setCreatedBy(getCurrentUser());

        entryDAO.saveOrUpdate(entry);
        topicDAO.saveOrUpdate(topic);
    }

    public void approveEntry(Long objid) {
        Entry entry = entryDAO.readEntry(objid);
        entry.setStatus(TopicStatus.APPROVED);
        entryDAO.saveOrUpdate(entry);
    }

    public void cancelEntry(Long objid) {
        Entry entry = entryDAO.readEntry(objid);
        entry.setStatus(TopicStatus.CANCELLED);
        entryDAO.saveOrUpdate(entry);
    }

    public List<Entry> listEntries(Long topicId) {
        Topic topic = topicDAO.readTopic(topicId);
        return entryDAO.readLastEntries(topic);
    }

    public Entry readEntry(Long objid) {
        return entryDAO.readEntry(objid);
    }

    public List<Entry> readMyEntries() {
        return entryDAO.readMyEntries(getCurrentUser());
    }

    public List<Entry> readWaitingEntrys() {
        return entryDAO.readWaitingEntrys();
    }

    public void updateEntry(Long objid, String content) {
        Entry entry = entryDAO.readEntry(objid);
        entry.setContent(content);
        entryDAO.saveOrUpdate(entry);
    }
}
