package nl.quintor.rc.jms;

import nl.quintor.rc.jms.util.JMSUtil;
import nl.quintor.rc.model.Overboeking;
import nl.quintor.rc.service.RekeningService;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OverboekingListenerTest {

    @InjectMocks
    private OverboekingListener listener = new OverboekingListener();
    @Mock
    private RekeningService rekeningServiceMock;

    @Rule
    public EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

    private Session session;
    private MessageProducer producer;

    @Before
    public void setup() throws JMSException {
        // Don't mock messageConverter, want to see the payload converted
        listener.setMessageConverter(JMSUtil.converter());

        ConnectionFactory connectionFactory = broker.createConnectionFactory();
        session = JMSUtil.session(connectionFactory);

        Destination destination = session.createTemporaryQueue();
        session.createConsumer(destination).setMessageListener(listener);
        producer = session.createProducer(destination);
    }

    @Test
    public void testOnMessage() throws Exception {

        Message message = session.createTextMessage("{\"id\":\"42\",\"boekdatum\":\"22-10-2014\",\"bedrag\":123.45,\"omschrijving\":\"description\",\"vanRekening\":\"vrekening\",\"tegenRekening\":\"trekening\"}");
        message.setStringProperty("JMSType", "nl.quintor.rc.web.rest.dto.OverboekingDto");

        ArgumentCaptor<Overboeking> overboekingCaptor = ArgumentCaptor.forClass(Overboeking.class);

        producer.send(message);
        // Give the consumer some time to get the message.....
        TimeUnit.SECONDS.sleep(1);

        verify(rekeningServiceMock).doeOverboeking(eq("vrekening"), overboekingCaptor.capture());

        Overboeking overboeking = overboekingCaptor.getValue();
        assertThat(overboeking.getId(), is(0L)); // LET OP! Deze wordt niet geset, daarom 0; anders zou dit 42 moeten zijn
        // assertThat(overboeking.getBoekdatum(), is("22-10-2014")); // Geen assert op boekdatum, de boekdatum die meegegeven wordt wordt niet gebruikt.
        assertThat(overboeking.getBedrag(), is(123.45d));
        assertThat(overboeking.getOmschrijving(), is("description"));
        assertThat(overboeking.getVanRekening(), is(nullValue())); // LET OP! Deze wordt niet geset, daarom null; anders zou dit vrekening moeten zijn
        assertThat(overboeking.getTegenRekening(), is("trekening"));
    }
}