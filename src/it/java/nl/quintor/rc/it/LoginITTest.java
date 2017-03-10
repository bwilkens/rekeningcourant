package nl.quintor.rc.it;

import nl.quintor.rc.model.Gebruiker;
import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.util.Date;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class LoginITTest extends AbstractIntegrationTest {

    @Test
    public void testLogin() {
        Gebruiker before = klantRepository.getKlantByKlantNummer(1);
        Date beforeLogin = before.getLaatstIngelogd() == null ? new Date() : before.getLaatstIngelogd();

        Response resp = ClientBuilder.newClient().target(SUT_URL + "/gebruiker/test").request().header("Authorization", "Basic dGVzdDp0ZXN0").get();

        assertThat("Expected response 200 (ok)", resp.getStatus(), is(200));
        Gebruiker gebruiker = klantRepository.getKlantByKlantNummer(1);
        assertThat("Laatst ingelogd mag niet null zijn", gebruiker.getLaatstIngelogd(), is(notNullValue()));
        assertThat("Laatst ingelogd mag niet gelijk zijn aan de vorige data", gebruiker.getLaatstIngelogd(), is(not(beforeLogin)));
        assertThat("Laatst ingelogd moet worden bijgewerkt bij het inloggen", gebruiker.getLaatstIngelogd().getTime(), is(greaterThan(beforeLogin.getTime())));
    }

}
