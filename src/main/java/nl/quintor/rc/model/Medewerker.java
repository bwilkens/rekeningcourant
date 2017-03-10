package nl.quintor.rc.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MEDEWERKER")
//@DiscriminatorValue("M")
public class Medewerker extends Gebruiker {

    private String medewerkerNummer;

    public String getMedewerkerNummer() {
        return medewerkerNummer;
    }

    public void setMedewerkerNummer(String medewerkerNummer) {
        this.medewerkerNummer = medewerkerNummer;
    }
}
