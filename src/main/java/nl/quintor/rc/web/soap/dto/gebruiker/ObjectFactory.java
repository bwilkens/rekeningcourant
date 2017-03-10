
package nl.quintor.rc.web.soap.dto.gebruiker;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the nl.quintor.rc.web.soap.dto.gebruiker package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: nl.quintor.rc.web.soap.dto.gebruiker
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Klant }
     * 
     */
    public Klant createKlant() {
        return new Klant();
    }

    /**
     * Create an instance of {@link Medewerker }
     * 
     */
    public Medewerker createMedewerker() {
        return new Medewerker();
    }

    /**
     * Create an instance of {@link Adres }
     * 
     */
    public Adres createAdres() {
        return new Adres();
    }

}
