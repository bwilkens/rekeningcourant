package nl.quintor.rc.jms;

import nl.quintor.rc.jms.util.JMSUtil;
import nl.quintor.rc.model.BetaalopdrachtStatus;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class BetaalopdrachtStatusSenderTest {

    private BetaalopdrachtStatusSender sender = new BetaalopdrachtStatusSender();

    @Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

    private Session session;
    private Destination destination;

    @Before
    public void setup() throws JMSException {
        // Don't mock messageConverter, want to see the payload converted
        sender.setMessageConverter(JMSUtil.converter());

        ConnectionFactory connectionFactory = broker.createConnectionFactory();
        session = JMSUtil.session(connectionFactory);

        destination = session.createTemporaryQueue();

        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultDestination(destination);

        sender.setBetaalopdrachtStatusTemplate(template);
    }

    @Test
    public void testSendStatusUpdate() throws JMSException {
        sender.sendStatusUpdate("42", BetaalopdrachtStatus.IN_BEHANDELING);

        MessageConsumer consumer = session.createConsumer(destination);
        TextMessage message = (TextMessage) consumer.receive(300);

        assertThat(message.getJMSType(), is("nl.quintor.rc.jms.dto.StatusUpdateDto"));
        assertThat(message.getText(), is("{\"betaalopdrachtId\":\"42\",\"status\":\"IN_BEHANDELING\",\"omschrijving\":\"De opdracht is in behandeling\"}"));
    }

}