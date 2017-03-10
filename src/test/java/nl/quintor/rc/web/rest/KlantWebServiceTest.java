package nl.quintor.rc.web.rest;

import com.google.common.collect.Lists;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.service.KlantService;
import nl.quintor.rc.service.RekeningService;
import nl.quintor.rc.web.rest.dto.KlantDto;
import nl.quintor.rc.web.rest.dto.RekeningDto;
import nl.quintor.rc.web.transformer.KlantTransformer;
import nl.quintor.rc.web.transformer.RekeningTransformer;
import org.easymock.Capture;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.StringContains.containsString;

public class KlantWebServiceTest extends JerseyTest {

    private RekeningService mockRekeningService;
    private KlantService mockKlantService;
    private KlantTransformer klantTransformer;
    private RekeningTransformer rekeningTransformer;

    private GenericApplicationContext context;

    @Before
    public void setup() {
    }

    @After
    public void after() {
        verifyAll();
    }

    @Override
    protected Application configure() {
        mockRekeningService = createMock(RekeningService.class);
        mockKlantService = createMock(KlantService.class);
        klantTransformer = new KlantTransformer();
        rekeningTransformer = new RekeningTransformer();

        DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
        parentBeanFactory.registerSingleton("rekeningService", mockRekeningService);
        parentBeanFactory.registerSingleton("klantService", mockKlantService);
        parentBeanFactory.registerSingleton("klantTransformer", klantTransformer);
        parentBeanFactory.registerSingleton("rekeningTransformer", rekeningTransformer);

        context = new GenericApplicationContext(parentBeanFactory);
        context.refresh();

        return new ResourceConfig() {
            {
                property("contextConfig", context);
                register(KlantWebService.class);
            }
        };
    }

    @Test
    public void testGetKlantenJson() {
        testGetKlanten(MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void testGetKlantenXml() {
        testGetKlanten(MediaType.APPLICATION_XML_TYPE);
    }

    private void testGetKlanten(MediaType mediaType) {
        String achternaam = "Janssen";
        Klant klant = new Klant();
        klant.setAchternaam(achternaam);
        expect(mockKlantService.getKlanten()).andReturn(Lists.newArrayList(klant));

        replayAll();

        Response response = target("klanten").request(mediaType).get();
        assertThat(response.getStatus(), is(200));
        List<KlantDto> result = response.readEntity(new GenericType<List<KlantDto>>() {
        });
        assertThat(result, hasSize(1));
    }

    @Test
    public void testAddKlantJson() {
        KlantDto klantDto = new KlantDto();
        klantDto.setGeslacht("MAN");
        klantDto.setAchternaam("pietersen");
        testAddKlant(MediaType.APPLICATION_JSON_TYPE, Entity.json(klantDto));
    }

    @Test
    public void testAddKlantXml() {
        KlantDto klantDto = new KlantDto();
        klantDto.setGeslacht("MAN");
        klantDto.setAchternaam("pietersen");
        testAddKlant(MediaType.APPLICATION_XML_TYPE, Entity.xml(klantDto));
    }

    private void testAddKlant(MediaType mediaType, Entity entity) {
        Capture<Klant> klantCapture = new Capture<>();
        mockKlantService.create(capture(klantCapture));

        replayAll();

        Response response = target("klanten").request(mediaType).post(entity);

        assertThat("Http 201 (created) verwacht", response.getStatus(), is(201));
        assertThat("No klant entity has been captured", klantCapture.hasCaptured());
        assertThat("Expected klant id in response path", response.getLocation().getPath(), containsString(String.valueOf(klantCapture.getValue().getId())));
        assertThat("Unexpected achternaam", klantCapture.getValue().getAchternaam(), is("pietersen"));
    }

    @Test
    public void testVerwijderKlantJson() {
        testVerwijderKlant(MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void testVerwijderKlantXml() {
        testVerwijderKlant(MediaType.APPLICATION_XML_TYPE);
    }

    private void testVerwijderKlant(MediaType mediaType) {
        Long id = 42L;
        mockKlantService.delete(id);

        replayAll();

        target("klanten/42").request(mediaType).delete();
    }

    @Test
    public void testGetKlantGegevensJson() {
        testGetKlantGegevens(MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void testGetKlantGegevensXml() {
        testGetKlantGegevens(MediaType.APPLICATION_XML_TYPE);
    }

    private void testGetKlantGegevens(MediaType mediaType) {
        Long klantnummer = 42L;
        String achternaam = "Janssen";
        Klant klant = new Klant();
        klant.setId(klantnummer);
        klant.setAchternaam(achternaam);
        expect(mockKlantService.getKlantByKlantNummer(klantnummer)).andReturn(klant);

        replayAll();

        Response response = target("klanten/42").request(mediaType).get();
        KlantDto klantDto = response.readEntity(KlantDto.class);
        assertThat(klantDto.getKlantnummer(), is(klantnummer));
        assertThat(klantDto.getAchternaam(), is(achternaam));
    }

    @Test
    public void testEditKlantJson() {
        Long klantnummer = 42L;
        String achternaam = "Janssen";
        KlantDto klantDto = new KlantDto();
        klantDto.setKlantnummer(klantnummer);
        klantDto.setAchternaam(achternaam);
        testEditKlant(klantnummer, MediaType.APPLICATION_JSON_TYPE, Entity.json(klantDto));
    }

    @Test
    public void testEditKlantXml() {
        Long klantnummer = 42L;
        String achternaam = "Janssen";
        KlantDto klantDto = new KlantDto();
        klantDto.setKlantnummer(klantnummer);
        klantDto.setAchternaam(achternaam);
        testEditKlant(klantnummer, MediaType.APPLICATION_XML_TYPE, Entity.xml(klantDto));
    }

    private void testEditKlant(Long klantnummer, MediaType mediaType, Entity entity) {
        Capture<Klant> klantCapture = new Capture<>();
        mockKlantService.updateKlant(capture(klantCapture));

        replayAll();

        Response response = target("klanten/" + klantnummer).request(mediaType).put(entity);
        assertThat(response.getStatus(), is(204));
        assertThat(klantCapture.hasCaptured(), is(true));
        Klant klant = klantCapture.getValue();
        assertThat(klant.getId(), is(klantnummer));
    }

    @Test
    public void testGetRekeningenJson() {
        testGetRekeningen(MediaType.APPLICATION_JSON_TYPE);
    }

    @Test
    public void testGetRekeningenXml() {
        testGetRekeningen(MediaType.APPLICATION_XML_TYPE);
    }

    private void testGetRekeningen(MediaType mediaType) {
        Long klantnummer = 42L;
        String rekeningNummer = "NL00XXX0123456789";
        Rekening rekening = new Rekening();
        rekening.setRekeningNummer(rekeningNummer);
        expect(mockRekeningService.getRekeningen(klantnummer)).andReturn(Lists.newArrayList(rekening));

        replayAll();

        Response response = target("klanten/42/rekeningen").request(mediaType).get();
        assertThat(response.getStatus(), is(200));
        List<RekeningDto> result = response.readEntity(new GenericType<List<RekeningDto>>() {
        });
        assertThat(result, hasSize(1));
    }

    public void replayAll() {
        replay(mockRekeningService);
        replay(mockKlantService);
    }

    public void verifyAll() {
        verify(mockRekeningService);
        verify(mockKlantService);
    }
}
