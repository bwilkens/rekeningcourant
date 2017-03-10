package nl.quintor.rc.web.rest;

import com.google.common.collect.Lists;
import nl.quintor.rc.model.Klant;
import nl.quintor.rc.model.Overboeking;
import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.service.RekeningService;
import nl.quintor.rc.web.rest.dto.KlantDto;
import nl.quintor.rc.web.rest.dto.OverboekingDto;
import nl.quintor.rc.web.rest.dto.RekeningDto;
import nl.quintor.rc.web.transformer.RekeningTransformer;
import org.easymock.Capture;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class RekeningWebServiceEasyMockTest extends JerseyTest {

    private RekeningService mockRekeningService;
    private RekeningTransformer rekeningTransformer;

    private GenericApplicationContext  context;

    @Before
    public void setup() {
    }

    @Override
    protected Application configure() {
        mockRekeningService = createMock(RekeningService.class);
        rekeningTransformer = new RekeningTransformer();

        DefaultListableBeanFactory parentBeanFactory = new DefaultListableBeanFactory();
        parentBeanFactory.registerSingleton("rekeningService", mockRekeningService);
        parentBeanFactory.registerSingleton("rekeningTransformer", rekeningTransformer);

        context = new GenericApplicationContext(parentBeanFactory);
        context.refresh();

        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig() {
            {
                property("contextConfig", context);
                register(RekeningWebService.class);
            }
        };
    }

    @Test
    public void testGetNietGoedGekeurderRekeningen() {
        String rekeningnummer = "XX00XXXX0123456789";
        String type = "Spaarrekening";

        Klant klant = new Klant();
        klant.setBsn(42L);

        Rekening rekening = new Rekening();
        rekening.setRekeningNummer(rekeningnummer);
        rekening.setType(type);
        rekening.addRekeninghouder(klant);
        expect(mockRekeningService.getGoedTeKeurenRekeningen()).andReturn(Lists.newArrayList(rekening));

        replayAll();

        Response response = target("rekeningen").queryParam("status", "INITIEEL").request().get();

        assertThat(response.getStatus(), is(200));
        List<RekeningDto> rekeningDtos = response.readEntity(new GenericType<List<RekeningDto>>(){});
        assertThat(rekeningDtos, hasSize(1));
        RekeningDto dto = rekeningDtos.get(0);
        assertThat(dto.getRekeningNummer(), is(rekeningnummer));
        assertThat(dto.getType(), is(type));
        assertThat(dto.getKlanten(), hasSize(1));
        assertThat(dto.getKlanten().get(0).getBsn(), is(42L));
    }

    @Test
    public void testGetNietGoedGekeurderRekeningenOnjuisteStatus() {
        Rekening rekening = new Rekening();
        expect(mockRekeningService.getGoedTeKeurenRekeningen()).andReturn(Lists.newArrayList(rekening));

        Response response = target("rekeningen").queryParam("status", "ANDERS").request().get();

        assertThat(response.getStatus(), is(412));
    }

    @Test
    public void testBlokkeeerRekening() {
        String rekeningnummer = "XX00XXXX0123456789";
        String reden = "Masterclass reden";
        mockRekeningService.blokkeerRekening(rekeningnummer, reden);
        expectLastCall().once();

        replayAll();

        Response response = target("rekeningen/"+rekeningnummer+"/acties/blokkeer").request().post(Entity.text(reden));
        assertThat(response.getStatus(), is(204));

        verifyAll();
    }

    @Test
    public void testRekeningGoedkeuren() {
        String rekeningnummer = "XX00XXXX0123456789";
        mockRekeningService.goedkeurenRekening(rekeningnummer);
        expectLastCall().once();

        replayAll();

        Response response = target("rekeningen/"+rekeningnummer+"/acties/goedkeuren").request().post(null);
        assertThat(response.getStatus(), is(204));

        verifyAll();
    }

    @Test
    public void testBeeindigRekening() {
        String rekeningnummer = "XX00XXXX0123456789";
        mockRekeningService.beeindigRekening(rekeningnummer);
        expectLastCall().once();

        replayAll();

        Response response = target("rekeningen/"+rekeningnummer).request().delete();
        assertThat(response.getStatus(), is(204));

        verifyAll();
    }

    @Test
    public void testCreateRekening() {
        KlantDto klantDto = new KlantDto();
        klantDto.setKlantnummer(42L);
        RekeningDto rekeningDto = new RekeningDto();
        rekeningDto.getKlanten().add(klantDto);
        rekeningDto.setType("betaalrekening");
        Collection<Rekening> rekeningen = Lists.newArrayList();

        Capture<Rekening> rekeningCapture = new Capture<>();
        Capture<List<Long>> klantenCapture = new Capture<>();

        mockRekeningService.createRekening(capture(rekeningCapture), capture(klantenCapture));
        expectLastCall().once();
        expect(mockRekeningService.getRekeningen(1)).andReturn(rekeningen);

        replayAll();

        Response response = target("rekeningen").request().post(Entity.json(rekeningDto));
        assertThat(response.getStatus(), is(201));
        assertThat(response.getLocation(), is(notNullValue()));

        assertThat(rekeningCapture.hasCaptured(), is(true));
        Rekening rekening = rekeningCapture.getValue();
        assertThat(rekening.getType(), is("betaalrekening"));

        assertThat(klantenCapture.hasCaptured(), is(true));
        List<Long> klanten = klantenCapture.getValue();
        assertThat(klanten, hasSize(1));
        assertThat(klanten, hasItem(42L));

        verifyAll();
    }

    @Test
    public void testCreateRekeningException() {
        List<Long> rekeningHouders = Lists.newArrayList(42L);
        KlantDto klant = new KlantDto();
        klant.setKlantnummer(42L);
        RekeningDto rekeningDto = new RekeningDto();
        rekeningDto.getKlanten().add(klant);

        Capture<Rekening> rekeningCapture = new Capture<>();

        mockRekeningService.createRekening(capture(rekeningCapture), eq(rekeningHouders));
        expectLastCall().andThrow(new NullPointerException());

        replayAll();

        Response response = target("rekeningen").request().post(Entity.json(rekeningDto));
        assertThat(response.getStatus(), is(500));

        verifyAll();
    }

    @Test
    public void testGetOverboekingen() {
        String rekeningnummer = "XX00XXXX0123456789";
        Date boekdatum = new Date();
        Double bedrag = Double.valueOf("2");
        String omschrijving = "Masterclass is top!";
        String tegenrekening = "YY99YYYY9876543210";
        Overboeking overboeking = new Overboeking();
        overboeking.setBoekdatum(boekdatum);
        overboeking.setBedrag(bedrag);
        overboeking.setOmschrijving(omschrijving);
        overboeking.setTegenRekening(tegenrekening);
        List<Overboeking> overboekingen = Lists.newArrayList(overboeking);
        expect(mockRekeningService.getOverboekingen(rekeningnummer)).andReturn(overboekingen);
        expectLastCall().once();

        replayAll();

        Response response = target("rekeningen/"+rekeningnummer+"/overboekingen").request().get();

        assertThat(response.getStatus(), is(200));
        List<OverboekingDto> overboekingDtos = response.readEntity(new GenericType<List<OverboekingDto>>(){});
        assertThat(overboekingDtos, hasSize(1));
        OverboekingDto dto = overboekingDtos.get(0);
        assertThat(dto.getBoekdatum(), is(notNullValue()));
        assertThat(dto.getBedrag(), is(bedrag));
        assertThat(dto.getOmschrijving(), is(omschrijving));
        assertThat(dto.getTegenRekening(), is(tegenrekening));
        verifyAll();
    }

    @Test
    public void testCreateOverboeking() {
        String rekeningnummer = "XX00XXXX0123456789";
        Double bedrag = Double.valueOf("2");
        String omschrijving = "Masterclass is top!";
        String tegenrekening = "YY99YYYY9876543210";

        OverboekingDto overboekingDto = new OverboekingDto();
        overboekingDto.setBedrag(bedrag);
        overboekingDto.setTegenRekening(tegenrekening);
        overboekingDto.setOmschrijving(omschrijving);

        Capture<Overboeking> overboekingCapture = new Capture<>();

        mockRekeningService.doeOverboeking(eq(rekeningnummer), capture(overboekingCapture));
        expectLastCall().once();

        replayAll();

        Response response = target("rekeningen/"+rekeningnummer+"/overboekingen").request().post(Entity.json(overboekingDto));

        assertThat(response.getStatus(), is(201));
        assertThat(overboekingCapture.hasCaptured(), is(true));
        Overboeking overboeking = overboekingCapture.getValue();
        assertThat(overboeking.getBedrag(), is(bedrag));
        assertThat(overboeking.getTegenRekening(), is(tegenrekening));
        assertThat(overboeking.getOmschrijving(), is(omschrijving));


        verifyAll();
    }

    public void replayAll() {
        replay(mockRekeningService);
    }

    public void verifyAll() {
        verify(mockRekeningService);
    }
}
