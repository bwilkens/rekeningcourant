package nl.quintor.rc.util.builders;

import nl.quintor.rc.model.Rekening;
import nl.quintor.rc.model.Status;

import java.util.Date;

public class RekeningBuilder {
    private String rekeningNummer;
    private Long version;
    private String type;
    private double saldo;
    private Status status = Status.INITIEEL;
    private Date beginDatum = new Date();
    private Date eindDatum;
    private Date datumGeblokkeerd;
    private String redenGeblokkeerd;

    private RekeningBuilder() {
    }

    public static RekeningBuilder aRekening() {
        return new RekeningBuilder();
    }

    public RekeningBuilder rekeningNummer(String rekeningNummer) {
        this.rekeningNummer = rekeningNummer;
        return this;
    }

    public RekeningBuilder version(Long version) {
        this.version = version;
        return this;
    }

    public RekeningBuilder type(String type) {
        this.type = type;
        return this;
    }

    public RekeningBuilder saldo(double saldo) {
        this.saldo = saldo;
        return this;
    }

    public RekeningBuilder status(Status status) {
        this.status = status;
        return this;
    }

    public RekeningBuilder beginDatum(Date beginDatum) {
        this.beginDatum = beginDatum;
        return this;
    }

    public RekeningBuilder eindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
        return this;
    }

    public RekeningBuilder datumGeblokkeerd(Date datumGeblokkeerd) {
        this.datumGeblokkeerd = datumGeblokkeerd;
        return this;
    }

    public RekeningBuilder redenGeblokkeerd(String redenGeblokkeerd) {
        this.redenGeblokkeerd = redenGeblokkeerd;
        return this;
    }

    public Rekening build() {
        Rekening rekening = new Rekening();
        rekening.setRekeningNummer(rekeningNummer);
        rekening.setVersion(version);
        rekening.setType(type);
        rekening.setSaldo(saldo);
        rekening.setStatus(status);
        rekening.setBeginDatum(beginDatum);
        rekening.setEindDatum(eindDatum);
        rekening.setDatumGeblokkeerd(datumGeblokkeerd);
        rekening.setRedenGeblokkeerd(redenGeblokkeerd);
        return rekening;
    }
}
