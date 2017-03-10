
package nl.quintor.rc.web.soap.dto.ophalenklant;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the nl.quintor.rc.web.soap.dto.ophalenklant package. 
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

    private final static QName _OphalenKlantRequest_QNAME = new QName("http://www.quintor.nl/rc/ophalenklant/1.0", "OphalenKlantRequest");
    private final static QName _OphalenKlantResponse_QNAME = new QName("http://www.quintor.nl/rc/ophalenklant/1.0", "OphalenKlantResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: nl.quintor.rc.web.soap.dto.ophalenklant
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OphalenKlantRequest }
     * 
     */
    public OphalenKlantRequest createOphalenKlantRequest() {
        return new OphalenKlantRequest();
    }

    /**
     * Create an instance of {@link OphalenKlantResponse }
     * 
     */
    public OphalenKlantResponse createOphalenKlantResponse() {
        return new OphalenKlantResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OphalenKlantRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.quintor.nl/rc/ophalenklant/1.0", name = "OphalenKlantRequest")
    public JAXBElement<OphalenKlantRequest> createOphalenKlantRequest(OphalenKlantRequest value) {
        return new JAXBElement<OphalenKlantRequest>(_OphalenKlantRequest_QNAME, OphalenKlantRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OphalenKlantResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.quintor.nl/rc/ophalenklant/1.0", name = "OphalenKlantResponse")
    public JAXBElement<OphalenKlantResponse> createOphalenKlantResponse(OphalenKlantResponse value) {
        return new JAXBElement<OphalenKlantResponse>(_OphalenKlantResponse_QNAME, OphalenKlantResponse.class, null, value);
    }

}
