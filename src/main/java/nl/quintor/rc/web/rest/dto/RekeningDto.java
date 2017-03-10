package nl.quintor.rc.web.rest.dto;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RekeningDto {
    private String rekeningNummer;
    private String type;
    private double saldo;
    private String status;
    private String beginDatum;
    private List<KlantDto> klanten = Lists.newArrayList();

    public List<KlantDto> getKlanten() {
        return klanten;
    }

    public void setKlanten(List<KlantDto> klanten) {
        this.klanten = klanten;
    }

    public String getRekeningNummer() {
        return rekeningNummer;
    }

    public void setRekeningNummer(String rekeningNummer) {
        this.rekeningNummer = rekeningNummer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBeginDatum() {
        return beginDatum;
    }

    public void setBeginDatum(String beginDatum) {
        this.beginDatum = beginDatum;
    }
}