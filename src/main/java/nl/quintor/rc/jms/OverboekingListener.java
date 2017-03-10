package nl.quintor.rc.jms;

import nl.quintor.rc.model.Overboeking;
import nl.quintor.rc.service.RekeningService;
import nl.quintor.rc.web.rest.dto.OverboekingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class OverboekingListener implements MessageListener {
    @Autowired
    private RekeningService rekeningService;
    @Autowired
    private MessageConverter messageConverter;

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void onMessage(final Message message) {
        try {
            CorrelationHolder.set(message.getJMSCorrelationID());

            final OverboekingDto overboekingDto = (OverboekingDto) messageConverter.fromMessage(message);
            final Overboeking overboeking = mapOverboeking(overboekingDto);

            rekeningService.doeOverboeking(overboekingDto.getVanRekening(), overboeking);
        } catch (JMSException e) {
            throw new IllegalStateException("Fout bij ontvangen JMS-bericht", e);
        } finally {
            CorrelationHolder.clear();
        }
    }

    private Overboeking mapOverboeking(final OverboekingDto overboekingDto) {
        final Overboeking overboeking = new Overboeking();
        overboeking.setBedrag(overboekingDto.getBedrag());
        overboeking.setTegenRekening(overboekingDto.getTegenRekening());
        overboeking.setOmschrijving(overboekingDto.getOmschrijving());
        return overboeking;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}
