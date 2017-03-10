package nl.quintor.rc.util.builders;

import nl.quintor.rc.model.Adres;
import nl.quintor.rc.model.Geslacht;
import nl.quintor.rc.model.Klant;

import java.util.Date;

public class KlantBuilder {
    private String voorletters;
    private String achternaam;
    private Geslacht geslacht;
    private Date geboorteDatum;
    private Date overlijdensDatum;
    private Long bsn;
    private String telefoonNummer;
    private String email;
    private Long id;
    private String loginnaam;
    private String password;
    private Date laatstIngelogd;
    private Date voorlaatstIngelogd;
    private Adres adres;

    private KlantBuilder() {
    }

    public static KlantBuilder aKlant() {
        return new KlantBuilder();
    }

    public KlantBuilder voorletters(String voorletters) {
        this.voorletters = voorletters;
        return this;
    }

    public KlantBuilder achternaam(String achternaam) {
        this.achternaam = achternaam;
        return this;
    }

    public KlantBuilder geslacht(Geslacht geslacht) {
        this.geslacht = geslacht;
        return this;
    }

    public KlantBuilder geboorteDatum(Date geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
        return this;
    }

    public KlantBuilder overlijdensDatum(Date overlijdensDatum) {
        this.overlijdensDatum = overlijdensDatum;
        return this;
    }

    public KlantBuilder bsn(Long bsn) {
        this.bsn = bsn;
        return this;
    }

    public KlantBuilder telefoonNummer(String telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
        return this;
    }

    public KlantBuilder email(String email) {
        this.email = email;
        return this;
    }

    public KlantBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public KlantBuilder loginnaam(String loginnaam) {
        this.loginnaam = loginnaam;
        return this;
    }

    public KlantBuilder password(String password) {
        this.password = password;
        return this;
    }

    public KlantBuilder laatstIngelogd(Date laatstIngelogd) {
        this.laatstIngelogd = laatstIngelogd;
        return this;
    }

    public KlantBuilder voorlaatstIngelogd(Date voorlaatstIngelogd) {
        this.voorlaatstIngelogd = voorlaatstIngelogd;
        return this;
    }

    public KlantBuilder adres(Adres adres) {
        this.adres = adres;
        return this;
    }


    public Klant build() {
        Klant klant = new Klant();
        klant.setVoorletters(voorletters);
        klant.setAchternaam(achternaam);
        klant.setGeslacht(geslacht);
        klant.setGeboorteDatum(geboorteDatum);
        klant.setOverlijdensDatum(overlijdensDatum);
        klant.setBsn(bsn);
        klant.setTelefoonNummer(telefoonNummer);
        klant.setEmail(email);
        klant.setId(id);
        klant.setLoginnaam(loginnaam);
        klant.setPassword(password);
        klant.setLaatstIngelogd(laatstIngelogd);
        klant.setVoorlaatstIngelogd(voorlaatstIngelogd);
        klant.setAdres(adres);
        return klant;
    }
}
