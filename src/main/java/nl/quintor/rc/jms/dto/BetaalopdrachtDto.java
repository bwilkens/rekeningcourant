package nl.quintor.rc.jms.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BetaalopdrachtDto {
    private String betaalopdrachtId;
    private String uitvoerdatum;
    private String debiteurNaam;
    private String debiteurIBAN;
    private String debiteurBIC;
    private String crediteurNaam;
    private String crediteurIBAN;
    private String crediteurBIC;
    private double bedrag;
    private String omschrijving;

    public String getBetaalopdrachtId() {
        return betaalopdrachtId;
    }

    public void setBetaalopdrachtId(String betaalopdrachtId) {
        this.betaalopdrachtId = betaalopdrachtId;
    }

    public String getUitvoerdatum() {
        return uitvoerdatum;
    }

    public void setUitvoerdatum(String uitvoerdatum) {
        this.uitvoerdatum = uitvoerdatum;
    }

    public String getDebiteurNaam() {
        return debiteurNaam;
    }

    public void setDebiteurNaam(String debiteurNaam) {
        this.debiteurNaam = debiteurNaam;
    }

    public String getDebiteurIBAN() {
        return debiteurIBAN;
    }

    public void setDebiteurIBAN(String debiteurIBAN) {
        this.debiteurIBAN = debiteurIBAN;
    }

    public String getDebiteurBIC() {
        return debiteurBIC;
    }

    public void setDebiteurBIC(String debiteurBIC) {
        this.debiteurBIC = debiteurBIC;
    }

    public String getCrediteurNaam() {
        return crediteurNaam;
    }

    public void setCrediteurNaam(String crediteurNaam) {
        this.crediteurNaam = crediteurNaam;
    }

    public String getCrediteurIBAN() {
        return crediteurIBAN;
    }

    public void setCrediteurIBAN(String crediteurIBAN) {
        this.crediteurIBAN = crediteurIBAN;
    }

    public String getCrediteurBIC() {
        return crediteurBIC;
    }

    public void setCrediteurBIC(String crediteurBIC) {
        this.crediteurBIC = crediteurBIC;
    }

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}