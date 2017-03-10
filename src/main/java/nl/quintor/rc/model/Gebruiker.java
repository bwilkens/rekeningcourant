package nl.quintor.rc.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="GEBRUIKER")
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "GEBRUIKER_TYPE")
@NamedQuery(name="findByLoginnaam", query="Select g from Gebruiker g where g.loginnaam = :loginnaam")
public abstract class Gebruiker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String loginnaam;

    private String password;

    private Date laatstIngelogd;

    @Transient
	private Date voorlaatstIngelogd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginnaam() {
		return loginnaam;
	}

	public void setLoginnaam(String loginnaam) {
		this.loginnaam = loginnaam;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLaatstIngelogd() {
		return laatstIngelogd;
	}

	public void setLaatstIngelogd(Date laatstIngelogd) {
		this.laatstIngelogd = laatstIngelogd;
	}

	public Date getVoorlaatstIngelogd() {
		return voorlaatstIngelogd;
	}

	public void setVoorlaatstIngelogd(Date voorlaatstIngelogd) {
		this.voorlaatstIngelogd = voorlaatstIngelogd;
	}
}