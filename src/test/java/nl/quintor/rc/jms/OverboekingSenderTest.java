package nl.quintor.rc.jms;

import nl.quintor.rc.jms.util.JMSUtil;
import nl.quintor.rc.model.Betaalopdracht;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.joda.time.LocalDate;
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

public class OverboekingSenderTest {

    private OverboekingSender sender = new OverboekingSender();

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

        sender.setOverboekingTemplate(template);
    }

    @Test
    public void testDoeOverboeking() throws JMSException {
        Betaalopdracht betaalopdracht = new Betaalopdracht();
        betaalopdracht.setBetaalopdrachtId("42");
        betaalopdracht.setUitvoerdatum(new LocalDate(2014, 10, 22).toDate());
        betaalopdracht.setDebiteurNaam("dnaam");
        betaalopdracht.setDebiteurIBAN("diban");
        betaalopdracht.setDebiteurBIC("dbic");
        betaalopdracht.setCrediteurNaam("cnaam");
        betaalopdracht.setCrediteurIBAN("ciban");
        betaalopdracht.setCrediteurBIC("cbic");
        betaalopdracht.setBedrag(123.45d);
        betaalopdracht.setOmschrijving("description");

        sender.doeOverboeking(betaalopdracht);

        MessageConsumer consumer = session.createConsumer(destination);
        TextMessage message = (TextMessage) consumer.receive(300);

        assertThat(message.getJMSType(), is("nl.quintor.rc.web.rest.dto.OverboekingDto"));
        assertThat(message.getText(), is("{\"id\":0,\"boekdatum\":\"22-10-2014\",\"bedrag\":123.45,\"omschrijving\":\"description\",\"vanRekening\":\"diban\",\"tegenRekening\":\"ciban\"}"));
    }

}