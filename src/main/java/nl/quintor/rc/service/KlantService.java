package nl.quintor.rc.service;

import nl.quintor.rc.model.Gebruiker;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.repository.GebruikerRepository;
import nl.quintor.rc.repository.KlantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
public class KlantService {

    static final Logger LOG = LoggerFactory.getLogger(KlantService.class);

	@Autowired
	KlantRepository klantRepository;

	@Autowired
	private GebruikerRepository gebruikerRepository;

	@Transactional
	@CacheEvict(value = "klanten", allEntries = true)
	public void create(Klant... klanten) {
        for (Klant klant : klanten) {
            klantRepository.create(klant);
        }
	}

	public Klant getKlantByLoginnaam(String loginnaam) {
        Gebruiker gebruiker = gebruikerRepository.getGebruikerByInlognaam(loginnaam);
        if (gebruiker instanceof Klant) {
            return (Klant) gebruiker;
        }
		return null;
	}

	@Cacheable("klanten")
	public Collection<Klant> getKlanten() {
		return klantRepository.getKlanten();
	}

	public Klant getKlantByKlantNummer(long id) {
		return klantRepository.getKlantByKlantNummer(id);
	}

	@Transactional
	@CacheEvict(value = "klanten", allEntries = true)
	public void updateKlant(Klant klant) {
        LOG.info("updating klant [{}]", klant.getId());
		klantRepository.save(klant);
	}

    @Transactional
	@CacheEvict(value = "klanten", allEntries = true)
    public void delete(long id) {
        LOG.info("deleting klant [{}]", id);
        Klant klant = klantRepository.getKlantByKlantNummer(id);
        klantRepository.delete(klant);
    }
}