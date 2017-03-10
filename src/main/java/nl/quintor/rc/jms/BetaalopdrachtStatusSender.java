package nl.quintor.rc.jms;

import nl.quintor.rc.jms.dto.StatusUpdateDto;
import nl.quintor.rc.model.BetaalopdrachtStatus;
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

@Component
public class BetaalopdrachtStatusSender {

    @Autowired
    private JmsTemplate betaalopdrachtStatusTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendStatusUpdate(final String betaalopdrachtId, final BetaalopdrachtStatus status) {
        final StatusUpdateDto statusUpdateDto = createStatusUpdateDto(betaalopdrachtId, status);

        betaalopdrachtStatusTemplate.send(new MessageCreator() {
                                        public Message createMessage(final Session session) throws JMSException {
                                            final Message message = messageConverter.toMessage(statusUpdateDto, session);
                                            message.setJMSCorrelationID(CorrelationHolder.get());
                                            return message;
                                        }
                                    }
        );
    }

    private StatusUpdateDto createStatusUpdateDto(final String betaalopdrachtId, final BetaalopdrachtStatus status) {
        final StatusUpdateDto dto = new StatusUpdateDto();
        dto.setBetaalopdrachtId(betaalopdrachtId);
        switch(status){
            case IN_BEHANDELING:
                dto.setOmschrijving("De opdracht is in behandeling");
                break;
            case AFGEKEURD:
                dto.setOmschrijving("De opdracht is afgekeurd");
                break;
            case GOEDGEKEURD:
                dto.setOmschrijving("De opdracht is goedgekeurd");
                break;
            case REJECTED:
                dto.setOmschrijving("De opdracht is niet uitvoerbaar");
                break;
            case RETURNED:
                dto.setOmschrijving("De opdracht is teruggestort");
                break;
        }
        dto.setStatus(status.toString());
        return dto;
    }

    public void setBetaalopdrachtStatusTemplate(JmsTemplate betaalopdrachtStatusTemplate) {
        this.betaalopdrachtStatusTemplate = betaalopdrachtStatusTemplate;
    }

    public void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}