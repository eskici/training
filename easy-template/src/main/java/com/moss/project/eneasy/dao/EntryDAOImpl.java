package com.moss.project.eneasy.dao;

import com.moss.project.eneasy.enums.TopicStatus;
import com.moss.project.eneasy.model.Entry;
import com.moss.project.eneasy.model.Topic;
import com.moss.project.eneasy.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EntryDAOImpl {

	private EntryRepository entryRepository;

	public List<Entry> readLastEntries(Topic topic){
		return entryRepository.findAllByTopicAndStatusOrderByLastChangeDateAsc(topic, TopicStatus.APPROVED);
	}

	public List<Entry> readMyEntries(UserEntity currentUser) {
		return entryRepository.findAllByCreatedByAndStatusOrderByLastChangeDate(currentUser, TopicStatus.APPROVED);
	}

	public List<Entry> readWaitingEntrys() {
		return entryRepository.findAllByStatusOrderByLastChangeDateAsc(TopicStatus.WAITING);
	}

	public Entry readEntry(Long objid){
		return entryRepository.findById(objid).orElse(null);
	}


	public void saveOrUpdate(Entry Entry){
		entryRepository.save(Entry);
	}

//	public List<Entry> searchEntry(String title){
//		 	Session session = getSessionFactory().getCurrentSession();
////			create a full text session
//			FullTextSession fSession = Search.getFullTextSession(session);
//			fSession.beginTransaction();
////			create a luceneQuery with a parser
//			QueryParser parser = new QueryParser(Version.LUCENE_20, title, new StandardAnalyzer(Version.LUCENE_20));
//			Query lucenceQuery = null;
//			try {
//				lucenceQuery = parser.parse("content:hibernate");
//
//			} catch (ParseException e) {
//				throw new RuntimeException("Cannot search with query string",e);
//			}
////			execute the query
//			List<Entry> Entrys = fSession.createFullTextQuery( new TermQuery(new Term("name", title)), Entry.class).list();
//			fSession.getTransaction().commit();
//			return Entrys;
//	}
//
//    public List<Entry> searchByKeyword(String keyword) {
//        BooleanQuery booleanQuery = new BooleanQuery();
//        booleanQuery.add( new PrefixQuery( new Term( "name", keyword ) ), BooleanClause.Occur.SHOULD );
////        booleanQuery.add( new PrefixQuery( new Term( "message", keyword ) ), BooleanClause.Occur.SHOULD );
//        org.hibernate.Query fullTextQuery = getFullTextSession().createFullTextQuery(booleanQuery, Entry.class);
//        return fullTextQuery.list();
//
//    }
//
//      public FullTextSession getFullTextSession() {
//        Session session = getSessionFactory().getCurrentSession();
//        FullTextSession fullTextSession = Search.createFullTextSession(session);
//        return fullTextSession;
//    }

}