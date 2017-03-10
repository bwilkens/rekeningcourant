
package nl.quintor.rc.web.soap.dto.gebruiker;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Gebruiker complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Gebruiker">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="voorletters" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="achternaam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="geslacht">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="MAN"/>
 *               &lt;enumeration value="VROUW"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="geboortedatum" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="adres" type="{http://www.quintor.nl/rc/gebruiker/1.0}Adres"/>
 *         &lt;element name="bsn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telefoonnummer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="email" type="{http://www.quintor.nl/rc/gebruiker/1.0}Email"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Gebruiker", propOrder = {
    "username",
    "password",
    "voorletters",
    "achternaam",
    "geslacht",
    "geboortedatum",
    "adres",
    "bsn",
    "telefoonnummer",
    "email"
})
@XmlSeeAlso({
    Klant.class,
    Medewerker.class
})
public abstract class Gebruiker {

    @XmlElement(required = true)
    protected String username;
    @XmlElement(required = true)
    protected String password;
    @XmlElement(required = true)
    protected String voorletters;
    @XmlElement(required = true)
    protected String achternaam;
    @XmlElement(required = true)
    protected String geslacht;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar geboortedatum;
    @XmlElement(required = true)
    protected Adres adres;
    @XmlElement(required = true)
    protected String bsn;
    @XmlElement(required = true)
    protected String telefoonnummer;
    @XmlElement(required = true)
    protected String email;

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the voorletters property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoorletters() {
        return voorletters;
    }

    /**
     * Sets the value of the voorletters property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoorletters(String value) {
        this.voorletters = value;
    }

    /**
     * Gets the value of the achternaam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAchternaam() {
        return achternaam;
    }

    /**
     * Sets the value of the achternaam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAchternaam(String value) {
        this.achternaam = value;
    }

    /**
     * Gets the value of the geslacht property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeslacht() {
        return geslacht;
    }

    /**
     * Sets the value of the geslacht property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeslacht(String value) {
        this.geslacht = value;
    }

    /**
     * Gets the value of the geboortedatum property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * Sets the value of the geboortedatum property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGeboortedatum(XMLGregorianCalendar value) {
        this.geboortedatum = value;
    }

    /**
     * Gets the value of the adres property.
     * 
     * @return
     *     possible object is
     *     {@link Adres }
     *     
     */
    public Adres getAdres() {
        return adres;
    }

    /**
     * Sets the value of the adres property.
     * 
     * @param value
     *     allowed object is
     *     {@link Adres }
     *     
     */
    public void setAdres(Adres value) {
        this.adres = value;
    }

    /**
     * Gets the value of the bsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBsn() {
        return bsn;
    }

    /**
     * Sets the value of the bsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBsn(String value) {
        this.bsn = value;
    }

    /**
     * Gets the value of the telefoonnummer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    /**
     * Sets the value of the telefoonnummer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefoonnummer(String value) {
        this.telefoonnummer = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

}
