package nl.quintor.rc.it;

import nl.quintor.rc.model.Geslacht;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.web.rest.dto.KlantDto;
import org.junit.Test;
import org.springframework.transaction.TransactionStatus;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class KlantITTest extends AbstractIntegrationTest {

    @Test
    public void testGetKlanten() {
        Response resp = ClientBuilder.newClient().target(SUT_URL + "/klanten").request().header("Authorization", "Basic YWRtaW46YWRtaW4=").get();

        assertThat("Expected response 200 (ok)", resp.getStatus(), is(200));
        List<KlantDto> result = resp.readEntity(new GenericType<List<KlantDto>>() {
        });
        assertThat(result, hasSize(3));
    }

    @Test
    public void testAddKlanten() {
        assertThat(klantRepository.getKlanten(), hasSize(2));

        KlantDto klantDto = new KlantDto();
        klantDto.setBsn(42L);
        klantDto.setGeslacht("MAN");
        klantDto.setVoorletters("p.");
        klantDto.setAchternaam("pietersen");

        Response resp = ClientBuilder.newClient().target(SUT_URL + "/klanten").request().header("Authorization", "Basic YWRtaW46YWRtaW4=").post(Entity.json(klantDto));

        TransactionStatus status = beginTransaction();
        assertThat("Expected response 201 (ok)", resp.getStatus(), is(201));
        assertThat(resp.getLocation().getPath(), is(notNullValue()));

        assertThat(klantRepository.getKlanten(), hasSize(3));

        Long id = Long.valueOf(resp.getLocation().getPath().replace("/", ""));
        Klant klant = klantRepository.getKlantByKlantNummer(id);

        assertThat(klant, is(notNullValue()));
        assertThat(klant.getGeslacht(), is(Geslacht.MAN));
        assertThat(klant.getBsn(), is(42L));
        assertThat(klant.getVoorletters(), is("p."));
        assertThat(klant.getAchternaam(), is("pietersen"));

        klantRepository.delete(klant);
        commitTransaction(status);
    }
}
