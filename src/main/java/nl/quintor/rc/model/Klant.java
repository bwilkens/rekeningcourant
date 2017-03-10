package nl.quintor.rc.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name="KLANT")
//@DiscriminatorValue("K")
public class Klant extends Gebruiker{

    @NotNull
    @Size(min=1, max=10)
	private String voorletters;

    @NotNull
    @Size(min=1, max=255)
    private String achternaam;

    @Enumerated(EnumType.STRING)
	private Geslacht geslacht;

    @Past
    @Temporal(TemporalType.DATE)
	private Date geboorteDatum;

    @Past
    @Temporal(TemporalType.DATE)
	private Date overlijdensDatum;

    @Embedded
    private Adres adres = new Adres();

    @NotNull
    private Long bsn;

    private String telefoonNummer;

	private String email;

	@ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name="KLANT_REKENING",
			joinColumns={@JoinColumn(name="klantId")},
			inverseJoinColumns={@JoinColumn(name="rekeningNummer")})
	private Collection<Rekening> rekeningen = new ArrayList<>();

	public String getVoorletters() {
		return voorletters;
	}

	public void setVoorletters(String voorletters) {
		this.voorletters = voorletters;
	}

    @Override
    public String toString() {
        return "KlantDto{" +
                "id=" + getId() +
                ", voorletters='" + voorletters + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geslacht='" + geslacht + '\'' +
                ", geboorteDatum=" + geboorteDatum +
                ", overlijdensDatum=" + overlijdensDatum +
                ", straat='" + adres.getStraat() + '\'' +
                ", huisnummer=" + adres.getHuisnummer() +
                ", huisnummerToevoeging='" + adres.getHuisnummerToevoeging() + '\'' +
                ", postcode='" + adres.getPostcode() + '\'' +
                ", woonplaats='" + adres.getWoonplaats() + '\'' +
                ", bsn=" + bsn +
                ", telefoonNummer='" + telefoonNummer + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public Geslacht getGeslacht() {
		return geslacht;
	}

	public void setGeslacht(Geslacht geslacht) {
		this.geslacht = geslacht;
	}

    public Adres getAdres() {
        return adres;
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

    public void addRekening(Rekening rekening){
        rekeningen.add(rekening);
    }

    public void verwijderRekening(Rekening rekening){
        rekeningen.remove(rekening);
    }

	public void setAdres(Adres adres) {
		this.adres = adres;
	}

}
