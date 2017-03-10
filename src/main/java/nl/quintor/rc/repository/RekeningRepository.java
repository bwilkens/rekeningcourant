package nl.quintor.rc.repository;

import nl.quintor.rc.model.Overboeking;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.model.Status;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

@Repository
public class RekeningRepository {

	@PersistenceContext(unitName = "RC_Unit")
	private EntityManager em;

	public Collection<Rekening> getByKlantNummer(long klantNummer) {
		TypedQuery<Rekening> query = em.createQuery("SELECT r FROM Rekening r JOIN r.rekeninghouders k WHERE k.id = :klantNummer", Rekening.class);
        query.setParameter("klantNummer", klantNummer);
		return query.getResultList();
	}

	public void create(Rekening rekening) {
		em.persist(rekening);
	}

    public void verwijderRekening(Rekening rekening) {
        em.remove(rekening);
    }

	public List<Rekening> getGoedTeKeurenRekeningen() {
		String queryStr = "SELECT r FROM Rekening r WHERE r.status = :status";
		TypedQuery<Rekening> query = em.createQuery(queryStr, Rekening.class);
		query.setParameter("status", Status.INITIEEL);

		return query.getResultList();
	}

	public Rekening getRekeningByRekeningnummer(String rekeningnummer) {
		String queryStr = "SELECT r FROM Rekening r WHERE r.rekeningNummer = :rekeningnummer";
		TypedQuery<Rekening> query = em.createQuery(queryStr, Rekening.class);
		query.setParameter("rekeningnummer", rekeningnummer);

		return query.getSingleResult();
	}

	public void saveRekening(Rekening rekening) {
		em.persist(rekening);
	}

    public Collection<Overboeking> getOverboekingen(String rekeningnummer) {
        String queryStr = "SELECT o FROM Overboeking o WHERE o.vanRekening.rekeningNummer = :rekeningnummer";

        TypedQuery<Overboeking> query = em.createQuery(queryStr, Overboeking.class);
        query.setParameter("rekeningnummer", rekeningnummer);

        return query.getResultList();

    }
}