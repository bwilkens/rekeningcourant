package nl.quintor.rc.jms;

import nl.quintor.rc.model.Betaalopdracht;
import nl.quintor.rc.web.rest.dto.OverboekingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.text.SimpleDateFormat;

@Component
public class OverboekingSender {
    @Autowired
    private JmsTemplate overboekingTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @Transactional(propagation = Propagation.MANDATORY)
    public void doeOverboeking(final Betaalopdracht betaalopdracht) {
        final OverboekingDto overboekingDto = createOverboekingDto(betaalopdracht);

        overboekingTemplate.send(new MessageCreator() {
                                              public Message createMessage(final Session session) throws JMSException {
                                                  final Message message = messageConverter.toMessage(overboekingDto, session);
                                                  message.setJMSCorrelationID(CorrelationHolder.get());
                                                  return message;
                                              }
                                          }
        );
    }

    private OverboekingDto createOverboekingDto(final Betaalopdracht betaalopdracht) {
        final OverboekingDto overboekingDto = new OverboekingDto();
        overboekingDto.setVanRekening(betaalopdracht.getDebiteurIBAN());
        overboekingDto.setTegenRekening(betaalopdracht.getCrediteurIBAN());
        overboekingDto.setBedrag(betaalopdracht.getBedrag());
        overboekingDto.setOmschrijving(betaalopdracht.getOmschrijving());
        overboekingDto.setBoekdatum(new SimpleDateFormat("dd-MM-yyyy").format(betaalopdracht.getUitvoerdatum()));
        return overboekingDto;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    public void setOverboekingTemplate(JmsTemplate overboekingTemplate) {
        this.overboekingTemplate = overboekingTemplate;
    }
}