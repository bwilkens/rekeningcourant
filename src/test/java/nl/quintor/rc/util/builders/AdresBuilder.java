package nl.quintor.rc.util.builders;

import nl.quintor.rc.model.Adres;

public class AdresBuilder {
    private String straat;
    private Integer huisnummer;
    private String huisnummerToevoeging;
    private String postcode;
    private String woonplaats;

    private AdresBuilder() {
    }

    public static AdresBuilder anAdres() {
        return new AdresBuilder();
    }

    public AdresBuilder straat(String straat) {
        this.straat = straat;
        return this;
    }

    public AdresBuilder huisnummer(Integer huisnummer) {
        this.huisnummer = huisnummer;
        return this;
    }

    public AdresBuilder huisnummerToevoeging(String huisnummerToevoeging) {
        this.huisnummerToevoeging = huisnummerToevoeging;
        return this;
    }

    public AdresBuilder postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public AdresBuilder woonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
        return this;
    }

    public Adres build() {
        Adres adres = new Adres();
        adres.setStraat(straat);
        adres.setHuisnummer(huisnummer);
        adres.setHuisnummerToevoeging(huisnummerToevoeging);
        adres.setPostcode(postcode);
        adres.setWoonplaats(woonplaats);
        return adres;
    }
}
