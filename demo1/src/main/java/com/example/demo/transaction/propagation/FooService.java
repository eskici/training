package com.example.demo.transaction.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Taner YILDIRIM
 */
@Service
public class FooService {

    @Autowired
    private BarService barService;

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation= Propagation.REQUIRED)
    public void foo() {
        em.persist(new Foo());
        barService.bar();
    }
}
