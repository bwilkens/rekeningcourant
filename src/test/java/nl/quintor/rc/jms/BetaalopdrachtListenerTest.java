package nl.quintor.rc.jms;

import nl.quintor.rc.jms.util.JMSUtil;
import nl.quintor.rc.model.Betaalopdracht;
import nl.quintor.rc.service.BetaalopdrachtService;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.joda.time.LocalDate;
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
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BetaalopdrachtListenerTest {

    @InjectMocks
    private BetaalopdrachtListener listener = new BetaalopdrachtListener();
    @Mock
    private BetaalopdrachtService betaalopdrachtServiceMock;

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

        Message message = session.createTextMessage("{\"betaalopdrachtId\":\"42\",\"uitvoerdatum\":\"22-10-2014\",\"debiteurNaam\":\"dnaam\",\"debiteurIBAN\":\"diban\",\"debiteurBIC\":\"dbic\",\"crediteurNaam\":\"cnaam\",\"crediteurIBAN\":\"ciban\",\"crediteurBIC\":\"cbic\",\"bedrag\":123.45,\"omschrijving\":\"description\"}");
        message.setStringProperty("JMSType", "nl.quintor.rc.jms.dto.BetaalopdrachtDto");

        ArgumentCaptor<Betaalopdracht> betaalopdrachtCaptor = ArgumentCaptor.forClass(Betaalopdracht.class);

        producer.send(message);
        // Give the consumer some time to get the message.....
        TimeUnit.SECONDS.sleep(1);

        verify(betaalopdrachtServiceMock).doeBetaalopdracht(betaalopdrachtCaptor.capture());

        Betaalopdracht betaalopdracht = betaalopdrachtCaptor.getValue();
        assertThat(betaalopdracht.getBetaalopdrachtId(), is("42"));
        assertThat(betaalopdracht.getUitvoerdatum(), is(new LocalDate(2014, 10, 22).toDate()));
        assertThat(betaalopdracht.getDebiteurNaam(), is("dnaam"));
        assertThat(betaalopdracht.getDebiteurIBAN(), is("diban"));
        assertThat(betaalopdracht.getDebiteurBIC(), is("dbic"));
        assertThat(betaalopdracht.getCrediteurNaam(), is("cnaam"));
        assertThat(betaalopdracht.getCrediteurIBAN(), is("ciban"));
        assertThat(betaalopdracht.getCrediteurBIC(), is("cbic"));
        assertThat(betaalopdracht.getBedrag(), is(123.45d));
        assertThat(betaalopdracht.getOmschrijving(), is("description"));
    }
}