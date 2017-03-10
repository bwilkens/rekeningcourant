
package nl.quintor.rc.web.soap.dto.aanmakengebruiker;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import nl.quintor.rc.web.soap.dto.gebruiker.Gebruiker;


/**
 * <p>Java class for AanmakenGebruikerResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AanmakenGebruikerResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gebruiker" type="{http://www.quintor.nl/rc/gebruiker/1.0}Gebruiker"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AanmakenGebruikerResponse", propOrder = {
    "gebruiker"
})
public class AanmakenGebruikerResponse {

    @XmlElement(required = true)
    protected Gebruiker gebruiker;

    /**
     * Gets the value of the gebruiker property.
     * 
     * @return
     *     possible object is
     *     {@link Gebruiker }
     *     
     */
    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    /**
     * Sets the value of the gebruiker property.
     * 
     * @param value
     *     allowed object is
     *     {@link Gebruiker }
     *     
     */
    public void setGebruiker(Gebruiker value) {
        this.gebruiker = value;
    }

}
