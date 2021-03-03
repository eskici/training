package com.example.jpatransaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Duration;
import java.time.Instant;

/**
 * @author Taner YILDIRIM
 */
@Service
public class BarService {

    @PersistenceContext
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRED)
    public void bar() {
        try {
            int recordCount = 500000;

            Instant start = Instant.now();
            for (int i = 0; i < recordCount; i++) {
                em.persist(new IdGeneration("IdGeneration_" + i));
            }

            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");


            start = Instant.now();
            for (int i = 0; i < recordCount; i++) {
                em.persist(new SeqGeneration("SeqGeneration_" + i));
            }
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");

        } catch (Exception ex) {
            //ignore ex...
        }
    }
}
