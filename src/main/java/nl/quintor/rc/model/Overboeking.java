package nl.quintor.rc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OVERBOEKING")
public class Overboeking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date boekdatum = new Date();

    private double bedrag;

    private String omschrijving;

    @ManyToOne(optional = false)
    @JoinColumn(name="vanRekening")
    private Rekening vanRekening;

    private String tegenRekening;

	public long getId() {
		return id;
	}

	public Date getBoekdatum() {
		return boekdatum;
	}

	public void setBoekdatum(Date boekdatum) {
		this.boekdatum = boekdatum;
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

	public Rekening getVanRekening() {
		return vanRekening;
	}

	public void setVanRekening(Rekening vanRekening) {
		this.vanRekening = vanRekening;
	}

	public String getTegenRekening() {
		return tegenRekening;
	}

	public void setTegenRekening(String tegenRekening) {
		this.tegenRekening = tegenRekening;
	}

}
