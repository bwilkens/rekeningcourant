package nl.quintor.rc.web.soap.klant;

import nl.quintor.rc.web.soap.dto.aanmakengebruiker.AanmakenGebruikerRequest;
import nl.quintor.rc.web.soap.dto.aanmakengebruiker.AanmakenGebruikerResponse;
import nl.quintor.rc.web.soap.dto.gebruiker.Adres;
import nl.quintor.rc.web.soap.dto.gebruiker.Gebruiker;
import nl.quintor.rc.web.soap.dto.gebruiker.Klant;
import nl.quintor.rc.web.soap.dto.ophalenklant.OphalenKlantRequest;
import nl.quintor.rc.web.soap.dto.ophalenklant.OphalenKlantResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class KlantWebServiceTest {

    private static KlantWebServiceImpl sut = new KlantWebServiceImpl();
    private static Endpoint endpoint;

    private static KlantWebService port;

    @BeforeClass
    public static void beforeClass() throws Exception {
        endpoint = Endpoint.publish("http://localhost:9999/ws/klant", sut);

        URL wsdlUrl = new URL("http://localhost:9999/ws/klant?wsdl");
        QName serviceName = new QName("http://klant.soap.web.rc.quintor.nl/", "KlantWebServiceImplService");
        Service service = Service.create(wsdlUrl, serviceName);

        port = service.getPort(KlantWebService.class);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        endpoint.stop();
    }

    @Test
    public void testGetHelloWorldAsString() throws MalformedURLException {
        OphalenKlantRequest request = new OphalenKlantRequest();
        request.setKlantnummer("84321684321");

        OphalenKlantResponse response = port.getKlantGegevens(request);
        assertThat(response, is(notNullValue()));
        assertThat(response.getKlant(), is(notNullValue()));
        Klant klant = response.getKlant();
        assertThat(klant.getKlantnummer(), is("84321684321"));
        assertThat(klant.getUsername(), is("Marcel"));
        assertThat(klant.getPassword(), is("MasterClass"));
        assertThat(klant.getAchternaam(), is("Hoekstra"));
        assertThat(klant.getBsn(), is("1094199960"));
        assertThat(klant.getGeboortedatum(), is(notNullValue()));
        assertThat(klant.getEmail(), is("hello@gmail.com"));
        assertThat(klant.getGeslacht(), is("Man"));
        assertThat(klant.getAdres().getHuisnummer(), is("13"));
        assertThat(klant.getAdres().getPostcode(), is("8531ZD"));
        assertThat(klant.getAdres().getStraat(), is("lemsterstraatje"));
        assertThat(klant.getAdres().getHuisnummerToevoeging(), is(nullValue()));
        assertThat(klant.getTelefoonnummer(), is("0651493872"));
    }

    @Test
    public void testCreateGebruiker() throws Exception {
        AanmakenGebruikerRequest request = new AanmakenGebruikerRequest();
        request.setUsername("Masterclass");
        request.setRol("MEDEWERKER");
        request.setAchternaam("Hoekstra");
        request.setBsn("1094123");
        request.setEmail("hello@gmail.com");
        request.setGeslacht("Man");
        final Adres adres = new Adres();
        adres.setHuisnummer("13");
        adres.setPostcode("8531ZD");
        adres.setStraat("lemsterstraatje");
        request.setTelefoonnummer("0651493872");

        request.setAdres(adres);

        AanmakenGebruikerResponse response = port.createGebruiker(request);

        assertThat(response, is(notNullValue()));
        assertThat(response.getGebruiker(), is(notNullValue()));
        Gebruiker gebruiker = response.getGebruiker();
        assertThat(gebruiker.getUsername(), is("Masterclass"));
        assertThat(gebruiker.getPassword(), is("geheim"));
        assertThat(gebruiker.getAchternaam(), is("Hoekstra"));
        assertThat(gebruiker.getBsn(), is("1094123"));
        assertThat(gebruiker.getGeboortedatum(), is(notNullValue()));
        assertThat(gebruiker.getEmail(), is("hello@gmail.com"));
        assertThat(gebruiker.getGeslacht(), is("Man"));
        assertThat(gebruiker.getAdres().getHuisnummer(), is("13"));
        assertThat(gebruiker.getAdres().getPostcode(), is("8531ZD"));
        assertThat(gebruiker.getAdres().getStraat(), is("lemsterstraatje"));
        assertThat(gebruiker.getTelefoonnummer(), is("0651493872"));
        assertThat(gebruiker.getAdres().getHuisnummerToevoeging(), is(nullValue()));


    }
}
