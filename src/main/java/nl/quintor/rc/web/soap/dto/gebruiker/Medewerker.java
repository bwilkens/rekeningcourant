
package nl.quintor.rc.web.soap.dto.gebruiker;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Medewerker complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Medewerker">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.quintor.nl/rc/gebruiker/1.0}Gebruiker">
 *       &lt;sequence>
 *         &lt;element name="medewerkerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Medewerker", propOrder = {
    "medewerkerId"
})
public class Medewerker
    extends Gebruiker
{

    @XmlElement(required = true)
    protected String medewerkerId;

    /**
     * Gets the value of the medewerkerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedewerkerId() {
        return medewerkerId;
    }

    /**
     * Sets the value of the medewerkerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedewerkerId(String value) {
        this.medewerkerId = value;
    }

}
