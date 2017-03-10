package nl.quintor.rc.web.rest.dto;

import nl.quintor.rc.web.rest.hal.HalSupport;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class GebruikerDto extends HalSupport {
	private String username;
	private String password;
    private String laatstIngelogd;
    private String displayName;
    private String rol;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getLaatstIngelogd() {
        return laatstIngelogd;
    }

    public void setLaatstIngelogd(String laatstIngelogd) {
        this.laatstIngelogd = laatstIngelogd;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
