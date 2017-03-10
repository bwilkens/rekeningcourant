package nl.quintor.rc.web.rest.dto;

import nl.quintor.rc.model.Rekening;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlRootElement
public class KlantDto extends GebruikerDto {
    @Min(message = "min waarde voor klantnummer is 1", value = 1)
    private Long klantnummer;
    @NotNull
    @Size(message = "min lengte voor voorletters is 1, max is 5", min = 1, max = 5)
    private String voorletters;
    private String achternaam;
    @NotNull
    @Pattern(message = "ongeldige waarde voor geslacht", regexp = "[MV]")
    private String geslacht;
    @Past(message = "geboorte datum moet in verleden liggen")
    private Date geboorteDatum;
    private Date overlijdensDatum;
    private String straat;
    private Integer huisnummer;
    private String huisnummerToevoeging;
    private String postcode;
    private String woonplaats;
    private Long bsn;
    private String telefoonNummer;
    private String email;
    private Collection<Rekening> rekeningen = new ArrayList<>();

    public Long getKlantnummer() {
        return klantnummer;
    }

    public void setKlantnummer(Long klantnummer) {
        this.klantnummer = klantnummer;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public Integer getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(Integer huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getHuisnummerToevoeging() {
        return huisnummerToevoeging;
    }

    public void setHuisnummerToevoeging(String huisnummerToevoeging) {
        this.huisnummerToevoeging = huisnummerToevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public Long getBsn() {
        return bsn;
    }

    public void setBsn(Long bsn) {
        this.bsn = bsn;
    }

    public String getTelefoonNummer() {
        return telefoonNummer;
    }

    public void setTelefoonNummer(String telefoonNummer) {
        this.telefoonNummer = telefoonNummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(Date geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public Date getOverlijdensDatum() {
        return overlijdensDatum;
    }

    public void setOverlijdensDatum(Date overlijdensDatum) {
        this.overlijdensDatum = overlijdensDatum;
    }

    public Collection<Rekening> getRekeningen() {
        return rekeningen;
    }

    public void setRekeningen(Collection<Rekening> rekeningen) {
        this.rekeningen = rekeningen;
    }

    public void addRekening(Rekening rekening) {
        rekeningen.add(rekening);
    }

}
