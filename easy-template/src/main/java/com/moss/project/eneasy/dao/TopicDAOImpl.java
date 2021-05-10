package com.moss.project.eneasy.dao;

import com.moss.project.eneasy.enums.EnumStatus;
import com.moss.project.eneasy.entity.Topic;
import com.moss.project.eneasy.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TopicDAOImpl {

	private TopicRepository topicRepository;

	@SuppressWarnings("unchecked")
	public List<Topic> readLastTopics(){
		return topicRepository.findAllByStatusOrderByLastChangeDateAsc(EnumStatus.APPROVED);
		/*
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Topic.class);
		criteria.setMaxResults(50);
		criteria.add(Expression.eq("status", MyConstants.STATUS_APPROVE));
		criteria.createAlias("entries", "entry");
		criteria.add(Expression.eq("entry.status", MyConstants.STATUS_APPROVE));
		criteria.addOrder(Order.asc("lastChangeDate"));
		return criteria.list();
		 */
	}

	@SuppressWarnings("unchecked")
	public List<Topic> listWaitingTopics(){
		return topicRepository.findAllByStatusOrderByLastChangeDateAsc(EnumStatus.WAITING);
	}

	@SuppressWarnings("unchecked")
	public List<Topic> readMyTopics(UserEntity user){
		return topicRepository.findAllByStatusAndCreatedByOrderByLastChangeDateAsc(EnumStatus.APPROVED, user);
/*
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Topic.class);
		criteria.add(Expression.eq("status", MyConstants.STATUS_APPROVE));
		criteria.addOrder(Order.asc("lastChangeDate"));
		criteria.createAlias("entries", "entry");
		criteria.add(Expression.eq("entry.createdBy", user));
		 criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
		*/
	}

	public Topic readTopic(Long objid){
		return topicRepository.findById(objid).orElse(null);
	}


	public Long saveOrUpdate(Topic topic){
		return topicRepository.save(topic).getId();
	}

//	public List<Topic> searchTopic(String title){
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
//			List<Topic> topics = fSession.createFullTextQuery( new TermQuery(new Term("name", title)), Topic.class).list();
//			fSession.getTransaction().commit();
//			return topics;
//	}
//
//    public List<Topic> searchByKeyword(String keyword) {
//        BooleanQuery booleanQuery = new BooleanQuery();
//        booleanQuery.add( new PrefixQuery( new Term( "name", keyword ) ), BooleanClause.Occur.SHOULD );
////        booleanQuery.add( new PrefixQuery( new Term( "message", keyword ) ), BooleanClause.Occur.SHOULD );
//        org.hibernate.Query fullTextQuery = getFullTextSession().createFullTextQuery(booleanQuery, Topic.class);
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