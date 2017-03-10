package nl.quintor.rc.jms;

import nl.quintor.rc.jms.dto.BetaalopdrachtDto;
import nl.quintor.rc.model.Betaalopdracht;
import nl.quintor.rc.service.BetaalopdrachtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class BetaalopdrachtListener implements MessageListener {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private BetaalopdrachtService betaalopdrachtService;
    @Autowired
    private MessageConverter messageConverter;

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void onMessage(final Message message) {
        try {
            CorrelationHolder.set(message.getJMSCorrelationID());

            final BetaalopdrachtDto betaalopdrachtDto = (BetaalopdrachtDto) messageConverter.fromMessage(message);
            final Betaalopdracht betaalopdracht = mapBetaalopdracht(betaalopdrachtDto);

            betaalopdrachtService.doeBetaalopdracht(betaalopdracht);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegale uitvoerdatum", e);
        } catch (JMSException e) {
            throw new IllegalStateException("Fout bij ontvangen JMS-bericht", e);
        } finally {
            CorrelationHolder.clear();
        }
    }

    private Betaalopdracht mapBetaalopdracht(final BetaalopdrachtDto betaalopdrachtDto) throws ParseException {
        checkNotNull(betaalopdrachtDto);

        final Betaalopdracht betaalopdracht = new Betaalopdracht();
        betaalopdracht.setBetaalopdrachtId(betaalopdrachtDto.getBetaalopdrachtId());
        betaalopdracht.setBedrag(betaalopdrachtDto.getBedrag());
        betaalopdracht.setBetaalopdrachtId(betaalopdrachtDto.getBetaalopdrachtId());
        betaalopdracht.setCrediteurBIC(betaalopdrachtDto.getCrediteurBIC());
        betaalopdracht.setCrediteurIBAN(betaalopdrachtDto.getCrediteurIBAN());
        betaalopdracht.setCrediteurNaam(betaalopdrachtDto.getCrediteurNaam());
        betaalopdracht.setDebiteurBIC(betaalopdrachtDto.getDebiteurBIC());
        betaalopdracht.setDebiteurIBAN(betaalopdrachtDto.getDebiteurIBAN());
        betaalopdracht.setDebiteurNaam(betaalopdrachtDto.getDebiteurNaam());
        betaalopdracht.setOmschrijving(betaalopdrachtDto.getOmschrijving());
        if (betaalopdrachtDto.getUitvoerdatum() != null) {
            betaalopdracht.setUitvoerdatum(DATE_FORMATTER.parse(betaalopdrachtDto.getUitvoerdatum()));
        }
        return betaalopdracht;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}
