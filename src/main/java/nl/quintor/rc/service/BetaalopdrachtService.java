package nl.quintor.rc.service;

import nl.quintor.rc.jms.BetaalopdrachtStatusSender;
import nl.quintor.rc.jms.OverboekingSender;
import nl.quintor.rc.jms.SwiftBetaalopdrachtSender;
import nl.quintor.rc.model.Betaalopdracht;
import nl.quintor.rc.model.BetaalopdrachtStatus;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.model.Status;
import nl.quintor.rc.repository.BetaalopdrachtRepository;
import nl.quintor.rc.repository.RekeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Service
public class BetaalopdrachtService {
    @Autowired
    private BetaalopdrachtRepository betaalopdrachtRepository;

    @Autowired
    private RekeningRepository rekeningRepository;

    @Autowired
    private OverboekingSender overboekingSender;

    @Autowired
    private SwiftBetaalopdrachtSender swiftBetaalopdrachtSender;

    @Autowired
    private BetaalopdrachtStatusSender betaalopdrachtStatusSender;

    @Transactional(propagation = Propagation.MANDATORY)
    public void doeBetaalopdracht(final Betaalopdracht betaalopdracht) {
        betaalopdrachtRepository.create(betaalopdracht);
        updateStatus(betaalopdracht, BetaalopdrachtStatus.IN_BEHANDELING);

        if(!validate(betaalopdracht)) {
            updateStatus(betaalopdracht, BetaalopdrachtStatus.AFGEKEURD);
            return;
        }
        updateStatus(betaalopdracht, BetaalopdrachtStatus.GOEDGEKEURD);
        overboekingSender.doeOverboeking(betaalopdracht);
        swiftBetaalopdrachtSender.sendBetaalopdracht(betaalopdracht);
    }

    private void updateStatus(final Betaalopdracht betaalopdracht, final BetaalopdrachtStatus status) {
        betaalopdracht.setStatus(status);
        betaalopdrachtStatusSender.sendStatusUpdate(betaalopdracht.getBetaalopdrachtId(), status);
    }

    private boolean validate(final Betaalopdracht betaalopdracht) {
        boolean valid = false;

        try {
            final Rekening rekening = rekeningRepository.getRekeningByRekeningnummer(betaalopdracht.getDebiteurIBAN());
            if(rekening != null && !rekening.getStatus().equals(Status.GEBLOKKEERD) && rekening.getSaldo() >= betaalopdracht.getBedrag()) {
                valid = true;
            }
        } catch (NoResultException e) {
            // Andere oplossing in repo?
        }

        return valid;
    }
}
