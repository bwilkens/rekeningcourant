package nl.quintor.rc.web.soap.klant;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import nl.quintor.rc.web.soap.dto.aanmakengebruiker.AanmakenGebruikerRequest;
import nl.quintor.rc.web.soap.dto.aanmakengebruiker.AanmakenGebruikerResponse;
import nl.quintor.rc.web.soap.dto.gebruiker.Adres;
import nl.quintor.rc.web.soap.dto.gebruiker.Gebruiker;
import nl.quintor.rc.web.soap.dto.gebruiker.Klant;
import nl.quintor.rc.web.soap.dto.gebruiker.Medewerker;
import nl.quintor.rc.web.soap.dto.ophalenklant.OphalenKlantRequest;
import nl.quintor.rc.web.soap.dto.ophalenklant.OphalenKlantResponse;

import org.joda.time.DateTime;

@WebService(endpointInterface = "nl.quintor.rc.web.soap.klant.KlantWebService")
public class KlantWebServiceImpl implements KlantWebService {

    @Override
    public OphalenKlantResponse getKlantGegevens(OphalenKlantRequest request) {
        OphalenKlantResponse response = new OphalenKlantResponse();
        Klant klant = new Klant();
        klant.setKlantnummer(request.getKlantnummer());
        klant.setUsername("Marcel");
        klant.setPassword("MasterClass");
        klant.setAchternaam("Hoekstra");
        klant.setBsn("1094199960");
        try {
            XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(new DateTime().toGregorianCalendar());
            klant.setGeboortedatum(xgc);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        klant.setEmail("hello@gmail.com");
        klant.setGeslacht("Man");
        Adres adres = new Adres();
        adres.setHuisnummer("13");
        adres.setPostcode("8531ZD");
        adres.setStraat("lemsterstraatje");
        adres.setWoonplaats("Lemmer");
        klant.setAdres(adres);
        klant.setTelefoonnummer("0651493872");
        response.setKlant(klant);

        return response;
    }

    @Override
    public AanmakenGebruikerResponse createGebruiker(AanmakenGebruikerRequest request) {
        AanmakenGebruikerResponse response = new AanmakenGebruikerResponse();

        Gebruiker gebruiker;
        switch (request.getRol()) {
            case "MEDEWERKER":
                gebruiker = new Medewerker();
                ((Medewerker) gebruiker).setMedewerkerId("1001");
                break;
            case "KLANT":
                gebruiker = new Klant();
                ((Klant) gebruiker).setKlantnummer("2002");
                break;
            default:
                throw new IllegalArgumentException("rol moet KLANT of MEDEWERKER zijn");
        }
        gebruiker.setUsername(request.getUsername());
        gebruiker.setPassword("geheim");
        gebruiker.setAchternaam(request.getAchternaam());
        gebruiker.setBsn(request.getBsn());
        try {
            XMLGregorianCalendar xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(new DateTime().toGregorianCalendar());
            gebruiker.setGeboortedatum(xgc);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        gebruiker.setEmail(request.getEmail());
        gebruiker.setGeslacht(request.getGeslacht());

        gebruiker.setAdres(request.getAdres());
        gebruiker.setTelefoonnummer(request.getTelefoonnummer());

        response.setGebruiker(gebruiker);

        return response;
    }

}
