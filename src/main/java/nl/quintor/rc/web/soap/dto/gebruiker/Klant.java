
package nl.quintor.rc.web.soap.dto.gebruiker;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Klant complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Klant">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.quintor.nl/rc/gebruiker/1.0}Gebruiker">
 *       &lt;sequence>
 *         &lt;element name="klantnummer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Klant", propOrder = {
    "klantnummer"
})
public class Klant
    extends Gebruiker
{

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
