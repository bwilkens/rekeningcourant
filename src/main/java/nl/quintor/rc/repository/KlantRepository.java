package nl.quintor.rc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.quintor.rc.model.Klant;

import org.springframework.stereotype.Repository;

@Repository
public class KlantRepository {

	@PersistenceContext(unitName = "RC_Unit")
	private EntityManager em;

    public void create(Klant klant) {
        em.persist(klant);
    }

    public void delete(Klant klant) {
        em.remove(klant);
    }

	public void save(Klant klant) {
		em.merge(klant);
	}

	public List<Klant> getKlanten() {
		TypedQuery<Klant> query = em.createQuery("SELECT k FROM Klant k", Klant.class);
        return query.getResultList();
	}
	
	public Klant getKlantByKlantNummer(long id) {
		return em.find(Klant.class, id);
	}
}