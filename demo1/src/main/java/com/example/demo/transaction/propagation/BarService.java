package com.example.demo.transaction.propagation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Taner YILDIRIM
 */
@Service
public class BarService {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void bar() {
        em.persist(new Baz());
        try {
            em.persist(new Bar());
        } catch(Exception ex) {
            //ignore ex...
        }
    }
}
