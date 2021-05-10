package com.moss.project.eneasy.service;

import com.moss.project.eneasy.dao.EntryRepository;
import com.moss.project.eneasy.dao.TopicDAOImpl;
import com.moss.project.eneasy.enums.EnumStatus;
import com.moss.project.eneasy.entity.Entry;
import com.moss.project.eneasy.entity.Topic;
import com.moss.project.eneasy.util.MyConstants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EntryService extends BaseService {

    private TopicDAOImpl topicDAO;

    private EntryRepository entryRepository;

    @Transactional
    public void addEntry(Long topicId, String content) {

        Topic topic = topicDAO.readTopic(topicId);
        topic.setLastChangeDate(new Date());

        Entry entry = new Entry();
        entry.setContent(content);
        entry.setTopic(topic);
        entry.setStatus(EnumStatus.WAITING);
        entry.setCreatedBy(getCurrentUser());

        entryRepository.save(entry);
        topicDAO.saveOrUpdate(topic);
    }

    public void updateEntryStatus(Long entryId, EnumStatus status) {
        Entry entry = entryRepository.findById(entryId).orElse(null);
        entry.setStatus(status);
        entryRepository.save(entry);
    }

    public void cancelEntry(Long entryId) {
        Entry entry = entryRepository.findById(entryId).orElse(null);
        entry.setStatus(EnumStatus.CANCELLED);
        entryRepository.save(entry);
    }

    public List<Entry> listEntries(Long topicId, int pageIndex) {
        Topic topic = topicDAO.readTopic(topicId);
        return entryRepository.findAllByTopicAndStatusOrderById(topic, EnumStatus.APPROVED, PageRequest.of(pageIndex, 10));
    }

    public List<Entry> readMyEntries() {
        return entryRepository.findAllByCreatedByAndStatusOrderByLastChangeDate(getCurrentUser(), EnumStatus.APPROVED);
    }

    public List<Entry> listWaitingEntrys() {
        return entryRepository.findAllByStatusOrderByLastChangeDateAsc(EnumStatus.WAITING);
    }

    public void updateEntry(Long entryId, String content) {
        Entry entry = entryRepository.findById(entryId).orElse(null);
        entry.setContent(content);
        entry.setLastChangeDate(new Date());
        entryRepository.save(entry);
    }

    public void deleteEntry(Long entryId) {
        Entry entry = entryRepository.findById(entryId).orElse(null);
        entry.setRecordStatus(MyConstants.RECORD_STATUS_DELETED);
        entry.setLastChangeDate(new Date());
        entryRepository.save(entry);
    }
}
