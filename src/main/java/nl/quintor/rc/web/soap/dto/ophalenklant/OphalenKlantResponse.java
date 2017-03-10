
package nl.quintor.rc.web.soap.dto.ophalenklant;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import nl.quintor.rc.web.soap.dto.gebruiker.Klant;


/**
 * <p>Java class for OphalenKlantResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OphalenKlantResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="klant" type="{http://www.quintor.nl/rc/gebruiker/1.0}Klant"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OphalenKlantResponse", propOrder = {
    "klant"
})
public class OphalenKlantResponse {

    @XmlElement(required = true)
    protected Klant klant;

    /**
     * Gets the value of the klant property.
     * 
     * @return
     *     possible object is
     *     {@link Klant }
     *     
     */
    public Klant getKlant() {
        return klant;
    }

    /**
     * Sets the value of the klant property.
     * 
     * @param value
     *     allowed object is
     *     {@link Klant }
     *     
     */
    public void setKlant(Klant value) {
        this.klant = value;
    }

}
