package nl.quintor.rc.repository;

import nl.quintor.rc.model.Betaalopdracht;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BetaalopdrachtRepository {
    @PersistenceContext(unitName = "RC_Unit")
    private EntityManager em;

    public void create(Betaalopdracht betaalopdracht) {
        em.persist(betaalopdracht);
    }
}
