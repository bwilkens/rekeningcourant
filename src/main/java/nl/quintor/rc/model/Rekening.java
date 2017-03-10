package nl.quintor.rc.model;

import org.apache.commons.collections4.ListUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "REKENING")
public class Rekening {

    @Id
    private String rekeningNummer;

    @Version
    private Long version;

    private String type;

    private double saldo;

    @Enumerated(EnumType.STRING)
    private Status status = Status.INITIEEL;

    private Date beginDatum = new Date();

    private Date eindDatum;

    private Date datumGeblokkeerd;

    private String redenGeblokkeerd;

    @ManyToMany(mappedBy = "rekeningen")
    private List<Klant> rekeninghouders = new ArrayList<>();

    @OneToMany(mappedBy = "vanRekening", cascade = CascadeType.PERSIST)
    private List<Overboeking> overboekingen = new ArrayList<>();

    public String getRekeningNummer() {
        return rekeningNummer;
    }

    public void setRekeningNummer(String rekeningNummer) {
        this.rekeningNummer = rekeningNummer;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getBeginDatum() {
        return beginDatum;
    }

    public void setBeginDatum(Date beginDatum) {
        this.beginDatum = beginDatum;
    }

    public Date getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
    }

    public Date getDatumGeblokkeerd() {
        return datumGeblokkeerd;
    }

    public void setDatumGeblokkeerd(Date datumGeblokkeerd) {
        this.datumGeblokkeerd = datumGeblokkeerd;
    }

    public String getRedenGeblokkeerd() {
        return redenGeblokkeerd;
    }

    public void setRedenGeblokkeerd(String redenGeblokkeerd) {
        this.redenGeblokkeerd = redenGeblokkeerd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Klant> getRekeninghouders() {
        return ListUtils.unmodifiableList(rekeninghouders);
    }

    public void addRekeninghouder(Klant rekeninghouder) {
        rekeninghouders.add(rekeninghouder);
        rekeninghouder.addRekening(this);
    }

    public void verwijderRekeninghouders() {
        for (Klant klant: rekeninghouders) {
            klant.verwijderRekening(this);
        }
        rekeninghouders.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rekening rekening = (Rekening) o;
        return rekeningNummer.equals(rekening.rekeningNummer);
    }

    @Override
    public int hashCode() {
        return rekeningNummer.hashCode();
    }

    @Override
    public String toString() {
        return "Rekening{" +
                "rekeningNummer='" + rekeningNummer + '\'' +
                ", type='" + type + '\'' +
                ", saldo=" + saldo +
                ", status='" + status + '\'' +
                ", beginDatum=" + beginDatum +
                ", eindDatum=" + eindDatum +
                ", datumGeblokkeerd=" + datumGeblokkeerd +
                ", redenGeblokkeerd='" + redenGeblokkeerd + '\'' +
                ", rekeninghouders=" + rekeninghouders +
                '}';
    }

    public List<Overboeking> getOverboekingen() {
        return overboekingen;
    }

    public void addOverboeking(Overboeking overboeking) {
        this.overboekingen.add(overboeking);
        overboeking.setVanRekening(this);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
        }
        this.saldo = this.saldo - overboeking.getBedrag();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}