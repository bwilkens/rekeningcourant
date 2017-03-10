package nl.quintor.rc.it;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.junit.JUnit4CitrusTestDesigner;
import com.consol.citrus.endpoint.Endpoint;
import com.consol.citrus.message.MessageType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.UUID;

public class BetaalopdrachtCitrusITTest extends JUnit4CitrusTestDesigner {

    @Autowired
    private Endpoint betaalopdrachtQueueEndpoint;

    @Autowired
    private DataSource dataSource;

    @Test
    @CitrusTest(name = "Betaalopdracht")
    public void testBetaalopdracht() {
        description("Betaalopdracht test");

        variable("id", System.currentTimeMillis());
        variable("correlationId", UUID.randomUUID().toString());
        variable("payload", "{\"betaalopdrachtId\":\"${id}\",\"uitvoerdatum\":\"22-10-2014\",\"debiteurNaam\":\"Omlo\",\"debiteurIBAN\":\"NL12RABO004324523\",\"debiteurBIC\":null,\"crediteurNaam\":\"Boere\",\"crediteurIBAN\":\"NL48RABO0789441742\",\"crediteurBIC\":null,\"bedrag\":123.45,\"omschrijving\":\"description\"}");

        query(dataSource).statement("select max(id) as max from overboeking")
                .extract("max", "maxid");

        query(dataSource).statement("select saldo - 123.45 as expectedSaldo from rekening where rekeningnummer = 'NL12RABO004324523'")
                .extract("expectedSaldo", "saldoDebiteur");

        query(dataSource).statement("select saldo as expectedSaldo from rekening where rekeningnummer = 'NL48RABO0789441742'")
                .extract("expectedSaldo", "saldoCrediteur");

        echo("Test betaalopdracht met JMSCorrelationID:${correlationId}");

        send(betaalopdrachtQueueEndpoint)
                .header("JMSCorrelationID", "${correlationId}")
                .header("JMSType", "nl.quintor.rc.jms.dto.BetaalopdrachtDto")
                .payload("${payload}");

        receive("betaalopdrachtStatusReceiver")
                .messageType(MessageType.JSON)
                .selector(Collections.singletonMap("JMSCorrelationID", "${correlationId}"))
                .payload("{\"betaalopdrachtId\":\"${id}\",\"status\":\"IN_BEHANDELING\",\"omschrijving\":\"De opdracht is in behandeling\"}")
                .timeout(2000);

        receive("betaalopdrachtStatusReceiver")
                .messageType(MessageType.JSON)
                .selector(Collections.singletonMap("JMSCorrelationID", "${correlationId}"))
                .payload("{\"betaalopdrachtId\":\"${id}\",\"status\":\"GOEDGEKEURD\",\"omschrijving\":\"De opdracht is goedgekeurd\"}")
                .timeout(2000);

        receive("swiftBetaalopdrachtenReceiver")
                .messageType(MessageType.JSON)
                .selector(Collections.singletonMap("JMSCorrelationID", "${correlationId}"))
                .payload("${payload}")
                .timeout(2000);

        query(dataSource).statement("select * from betaalopdracht where betaalopdrachtid = ${id}")
                .validate("uitvoerdatum", "2014-10-22 00:00:00.0")
                .validate("debiteurNaam", "Omlo")
                .validate("debiteurIBAN", "NL12RABO004324523")
                .validate("debiteurBIC", "")
                .validate("crediteurNaam", "Boere")
                .validate("crediteurIBAN", "NL48RABO0789441742")
                .validate("crediteurBIC", "")
                .validate("bedrag", "123.45")
                .validate("omschrijving", "description")
                .validate("status", "GOEDGEKEURD");

        echo("Give the application some time to finish the transaction");
        sleep(3.0);

        query(dataSource).statement("select * from overboeking where id > ${maxid}")
                .validate("vanRekening", "NL12RABO004324523")
                .validate("tegenRekening", "NL48RABO0789441742")
                .validate("bedrag", "123.45")
                .validate("omschrijving", "description");

        query(dataSource).statement("select * from rekening where rekeningnummer = 'NL12RABO004324523' ")
                .validate("saldo", "${saldoDebiteur}");

        // We gaan pas opboeken na swift???
        query(dataSource).statement("select * from rekening where rekeningnummer = 'NL48RABO0789441742'")
                .validate("saldo", "${saldoCrediteur}");
    }

    @Test
    @CitrusTest(name = "OnjuisteBetaalopdracht")
    public void testOnjuisteBetaalopdracht() {
        description("OnjuisteBetaalopdracht test");

        variable("id", System.currentTimeMillis());
        variable("correlationId", UUID.randomUUID().toString());
        variable("payload", "{\"betaalopdrachtId\":\"${id}\",\"uitvoerdatum\":\"22-10-2014\",\"debiteurNaam\":\"dnaam\",\"debiteurIBAN\":\"diban\",\"debiteurBIC\":\"dbic\",\"crediteurNaam\":\"cnaam\",\"crediteurIBAN\":\"ciban\",\"crediteurBIC\":\"cbic\",\"bedrag\":123.45,\"omschrijving\":\"description\"}");

        echo("Test onjuiste betaalopdracht met JMSCorrelationID:${correlationId}");

        send(betaalopdrachtQueueEndpoint)
                .header("JMSCorrelationID", "${correlationId}")
                .header("JMSType", "nl.quintor.rc.jms.dto.BetaalopdrachtDto")
                .payload("${payload}");

        receive("betaalopdrachtStatusReceiver")
                .messageType(MessageType.JSON)
                .selector(Collections.singletonMap("JMSCorrelationID", "${correlationId}"))
                .payload("{\"betaalopdrachtId\":\"${id}\",\"status\":\"IN_BEHANDELING\",\"omschrijving\":\"De opdracht is in behandeling\"}")
                .timeout(2000);

        receive("betaalopdrachtStatusReceiver")
                .messageType(MessageType.JSON)
                .selector(Collections.singletonMap("JMSCorrelationID", "${correlationId}"))
                .payload("{\"betaalopdrachtId\":\"${id}\",\"status\":\"AFGEKEURD\",\"omschrijving\":\"De opdracht is afgekeurd\"}")
                .timeout(2000);

        query(dataSource).statement("select * from betaalopdracht where betaalopdrachtid = ${id}")
                .validate("uitvoerdatum", "2014-10-22 00:00:00.0")
                .validate("debiteurNaam", "dnaam")
                .validate("debiteurIBAN", "diban")
                .validate("debiteurBIC", "dbic")
                .validate("crediteurNaam", "cnaam")
                .validate("crediteurIBAN", "ciban")
                .validate("crediteurBIC", "cbic")
                .validate("bedrag", "123.45")
                .validate("omschrijving", "description")
                .validate("status", "AFGEKEURD");
    }

}
