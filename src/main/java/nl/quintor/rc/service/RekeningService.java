package nl.quintor.rc.service;

import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Overboeking;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.model.Status;
import nl.quintor.rc.repository.KlantRepository;
import nl.quintor.rc.repository.RekeningRepository;
import nl.quintor.rc.service.util.IbanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RekeningService {
    @Autowired
    RekeningRepository rekeningRepository;
    @Autowired
    KlantRepository klantRepository;
    @Autowired
    IbanFactory ibanFactory;

    private static final Logger LOG = LoggerFactory.getLogger(RekeningService.class);

    @Transactional(readOnly = true)
    public List<Rekening> getGoedTeKeurenRekeningen() {
        return rekeningRepository.getGoedTeKeurenRekeningen();
    }

    public void createRekening(Rekening rekening, List<Long> rekeningHouders) {
        rekening.setRekeningNummer(ibanFactory.createRabobankIBAN());
        rekeningRepository.create(rekening);

        for (Long klantId : rekeningHouders) {
            Klant klant = klantRepository.getKlantByKlantNummer(klantId);
            rekening.addRekeninghouder(klant);
        }
    }

    public void blokkeerRekening(String rekeningnummer, String reden) {
        Rekening rekening = rekeningRepository.getRekeningByRekeningnummer(rekeningnummer);
        rekening.setRedenGeblokkeerd(reden);
        rekening.setDatumGeblokkeerd(new Date());
        rekening.setStatus(Status.GEBLOKKEERD);
        rekeningRepository.saveRekening(rekening);

        LOG.debug("rekening {} geblokkeerd; reden = {}", rekeningnummer, reden);
    }

    @Transactional(readOnly = true)
    public Collection<Rekening> getRekeningen(long klantNummer) {
        return rekeningRepository.getByKlantNummer(klantNummer);
    }

    @Transactional(readOnly = true)
    public Collection<Overboeking> getOverboekingen(String rekeningnummer) {
        return rekeningRepository.getOverboekingen(rekeningnummer);
    }


    public void goedkeurenRekening(String rekeningnummer) {
        LOG.debug("rekening {} goedgekeurd", rekeningnummer);
    }

    public void beeindigRekening(String rekeningnummer) {
        Rekening rekening = rekeningRepository.getRekeningByRekeningnummer(rekeningnummer);
        rekening.verwijderRekeninghouders();
        rekeningRepository.verwijderRekening(rekening);
        LOG.debug("rekening '{}' beeindigd", rekeningnummer);
    }

    public void doeOverboeking(String rekeningnummer, Overboeking overboeking) {
        LOG.debug("eur {} overschrijven van {} naar {} met omschrijving '{}'", overboeking.getBedrag(), rekeningnummer, overboeking.getTegenRekening(), overboeking.getOmschrijving());
        Rekening rekening = rekeningRepository.getRekeningByRekeningnummer(rekeningnummer);
        rekening.addOverboeking(overboeking);
    }
}