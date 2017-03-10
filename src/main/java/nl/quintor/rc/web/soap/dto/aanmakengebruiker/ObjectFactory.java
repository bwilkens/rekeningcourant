
package nl.quintor.rc.web.soap.dto.aanmakengebruiker;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the nl.quintor.rc.web.soap.dto.aanmakengebruiker package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AanmakenGebruikerException_QNAME = new QName("http://www.quintor.nl/rc/gebruikeraanmaken/1.0", "AanmakenGebruikerException");
    private final static QName _AanmakenGebruikerResponse_QNAME = new QName("http://www.quintor.nl/rc/gebruikeraanmaken/1.0", "AanmakenGebruikerResponse");
    private final static QName _AanmakenGebruikerRequest_QNAME = new QName("http://www.quintor.nl/rc/gebruikeraanmaken/1.0", "AanmakenGebruikerRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: nl.quintor.rc.web.soap.dto.aanmakengebruiker
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AanmakenGebruikerResponse }
     * 
     */
    public AanmakenGebruikerResponse createAanmakenGebruikerResponse() {
        return new AanmakenGebruikerResponse();
    }

    /**
     * Create an instance of {@link AanmakenGebruikerRequest }
     * 
     */
    public AanmakenGebruikerRequest createAanmakenGebruikerRequest() {
        return new AanmakenGebruikerRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.quintor.nl/rc/gebruikeraanmaken/1.0", name = "AanmakenGebruikerException")
    public JAXBElement<String> createAanmakenGebruikerException(String value) {
        return new JAXBElement<String>(_AanmakenGebruikerException_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AanmakenGebruikerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.quintor.nl/rc/gebruikeraanmaken/1.0", name = "AanmakenGebruikerResponse")
    public JAXBElement<AanmakenGebruikerResponse> createAanmakenGebruikerResponse(AanmakenGebruikerResponse value) {
        return new JAXBElement<AanmakenGebruikerResponse>(_AanmakenGebruikerResponse_QNAME, AanmakenGebruikerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AanmakenGebruikerRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.quintor.nl/rc/gebruikeraanmaken/1.0", name = "AanmakenGebruikerRequest")
    public JAXBElement<AanmakenGebruikerRequest> createAanmakenGebruikerRequest(AanmakenGebruikerRequest value) {
        return new JAXBElement<AanmakenGebruikerRequest>(_AanmakenGebruikerRequest_QNAME, AanmakenGebruikerRequest.class, null, value);
    }

}
