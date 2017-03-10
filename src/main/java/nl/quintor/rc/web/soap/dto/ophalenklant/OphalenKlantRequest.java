
package nl.quintor.rc.web.soap.dto.ophalenklant;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OphalenKlantRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OphalenKlantRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="klantnummer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OphalenKlantRequest", propOrder = {
    "klantnummer"
})
public class OphalenKlantRequest {

    @XmlElement(required = true)
    protected String klantnummer;

    /**
     * Gets the value of the klantnummer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKlantnummer() {
        return klantnummer;
    }

    /**
     * Sets the value of the klantnummer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKlantnummer(String value) {
        this.klantnummer = value;
    }

}
