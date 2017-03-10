package nl.quintor.rc.web.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OverboekingDto {
	private long id;
	private String boekdatum;
	private double bedrag;
	private String omschrijving;
	private String vanRekening;
	private String tegenRekening;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public String getBoekdatum() {
        return boekdatum;
    }

    public void setBoekdatum(String boekdatum) {
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

	public String getVanRekening() {
		return vanRekening;
	}

	public void setVanRekening(String vanRekening) {
		this.vanRekening = vanRekening;
	}

	public String getTegenRekening() {
		return tegenRekening;
	}

	public void setTegenRekening(String tegenRekening) {
		this.tegenRekening = tegenRekening;
	}
}
