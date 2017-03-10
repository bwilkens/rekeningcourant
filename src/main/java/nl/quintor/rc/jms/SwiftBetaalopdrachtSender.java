package nl.quintor.rc.jms;

import nl.quintor.rc.jms.dto.BetaalopdrachtDto;
import nl.quintor.rc.model.Betaalopdracht;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.text.SimpleDateFormat;

@Service
public class SwiftBetaalopdrachtSender {
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private JmsTemplate swiftBetaalopdrachtTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @Transactional(propagation = Propagation.MANDATORY)
    public void sendBetaalopdracht(final Betaalopdracht betaalopdracht) {
        final BetaalopdrachtDto betaalopdrachtDto = mapBetaalopdracht(betaalopdracht);

        swiftBetaalopdrachtTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                final Message message = messageConverter.toMessage(betaalopdrachtDto, session);
                message.setJMSCorrelationID(CorrelationHolder.get());
                return message;
            }
        });
    }

    private BetaalopdrachtDto mapBetaalopdracht(final Betaalopdracht betaalopdracht) {
        final BetaalopdrachtDto betaalopdrachtDto = new BetaalopdrachtDto();
        betaalopdrachtDto.setBetaalopdrachtId(betaalopdracht.getBetaalopdrachtId());
        betaalopdrachtDto.setBedrag(betaalopdracht.getBedrag());
        betaalopdrachtDto.setBetaalopdrachtId(betaalopdracht.getBetaalopdrachtId());
        betaalopdrachtDto.setCrediteurBIC(betaalopdracht.getCrediteurBIC());
        betaalopdrachtDto.setCrediteurIBAN(betaalopdracht.getCrediteurIBAN());
        betaalopdrachtDto.setCrediteurNaam(betaalopdracht.getCrediteurNaam());
        betaalopdrachtDto.setDebiteurBIC(betaalopdracht.getDebiteurBIC());
        betaalopdrachtDto.setDebiteurIBAN(betaalopdracht.getDebiteurIBAN());
        betaalopdrachtDto.setDebiteurNaam(betaalopdracht.getDebiteurNaam());
        betaalopdrachtDto.setOmschrijving(betaalopdracht.getOmschrijving());
        if(betaalopdracht.getUitvoerdatum() != null) {
            betaalopdrachtDto.setUitvoerdatum(DATE_FORMATTER.format(betaalopdracht.getUitvoerdatum()));
        }
        return betaalopdrachtDto;
    }

    public void setSwiftBetaalopdrachtTemplate(JmsTemplate swiftBetaalopdrachtTemplate) {
        this.swiftBetaalopdrachtTemplate = swiftBetaalopdrachtTemplate;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}