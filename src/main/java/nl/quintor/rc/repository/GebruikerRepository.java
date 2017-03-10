package nl.quintor.rc.repository;

import nl.quintor.rc.model.Gebruiker;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
public class GebruikerRepository {

    @PersistenceContext(unitName = "RC_Unit")
    private EntityManager em;

    public Gebruiker getGebruikerByInlognaam(String username) {
        String queryStr = "SELECT g FROM Gebruiker g WHERE g.loginnaam = :loginnaam";
        TypedQuery<Gebruiker> query = em.createQuery(queryStr, Gebruiker.class);
        query.setParameter("loginnaam", username);

        return query.getSingleResult();
    }

    public Gebruiker getGebruikerByInlognaamNamed(String username) {
        TypedQuery<Gebruiker> query = em.createNamedQuery("findByLoginnaam", Gebruiker.class);
        query.setParameter("loginnaam", username);
        return query.getSingleResult();
    }

    public Gebruiker getGebruikerByInlognaamCriteria(String username) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Gebruiker> criteriaQuery = builder.createQuery(Gebruiker.class);
        Root<Gebruiker> root = criteriaQuery.from(Gebruiker.class);
        criteriaQuery.where(builder.equal(root.get("loginnaam"), builder.parameter(String.class, "loginnaam")));
        TypedQuery<Gebruiker> query = em.createQuery(criteriaQuery);
        query.setParameter("loginnaam", username);
        return query.getSingleResult();
    }

    public void create(Gebruiker gebruiker) {
        em.persist(gebruiker);
    }
}