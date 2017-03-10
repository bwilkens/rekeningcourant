package nl.quintor.rc.web.soap.klant;

import nl.quintor.rc.web.soap.dto.aanmakengebruiker.AanmakenGebruikerRequest;
import nl.quintor.rc.web.soap.dto.aanmakengebruiker.AanmakenGebruikerResponse;
import nl.quintor.rc.web.soap.dto.ophalenklant.OphalenKlantRequest;
import nl.quintor.rc.web.soap.dto.ophalenklant.OphalenKlantResponse;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class KlantWebServiceClient {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:9999/ws/klant?wsdl");

        //Represents a qualified name as per XML Schema's definition. The value of a QName contains a Namespace URI, local part and prefix.
        QName qname = new QName("http://klant.soap.web.rc.quintor.nl/", "KlantWebServiceImplService");

        //Service objects provide the client view of a Web service.
        Service service = Service.create(url, qname);

        //The getPort method returns a proxy. A service client uses this proxy to invoke operations on the target service endpoint.
        //The serviceEndpointInterface specifies the service endpoint interface that is supported by the created dynamic proxy instance.
        KlantWebService klantPort = service.getPort(KlantWebService.class);
        OphalenKlantRequest ophalenKlantRequest = new OphalenKlantRequest();
        ophalenKlantRequest.setKlantnummer("111");
        OphalenKlantResponse ophalenKlantResponse = klantPort.getKlantGegevens(ophalenKlantRequest);
        System.out.println("result=" + ophalenKlantResponse.getKlant().getUsername());


        AanmakenGebruikerRequest aanmakenGebruikerRequest = new AanmakenGebruikerRequest();
        aanmakenGebruikerRequest.setUsername("Marcel");
        AanmakenGebruikerResponse responseType = klantPort.createGebruiker(aanmakenGebruikerRequest);
        System.out.println("result=" + responseType.getGebruiker().getUsername());

    }
}