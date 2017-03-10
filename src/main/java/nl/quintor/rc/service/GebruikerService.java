package nl.quintor.rc.service;

import nl.quintor.rc.model.Gebruiker;
import nl.quintor.rc.repository.GebruikerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class GebruikerService {

	@Autowired
	GebruikerRepository gebruikerRepository;

	private static final Logger LOG = LoggerFactory.getLogger(GebruikerService.class);

	public Gebruiker inloggenGebruiker(String loginnaam) {
        Gebruiker gebruiker = gebruikerRepository.getGebruikerByInlognaam(loginnaam);
        LOG.info("Gebruiker voor inlognaam {} gevonden met id {}", loginnaam, gebruiker.getId());
		gebruiker.setVoorlaatstIngelogd(gebruiker.getLaatstIngelogd());
		gebruiker.setLaatstIngelogd(new Date());
        return gebruiker;
	}

    public void toevoegenGebruiker(Gebruiker gebruiker) {
        gebruikerRepository.create(gebruiker);
    }
}