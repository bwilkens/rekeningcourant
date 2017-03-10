package nl.quintor.rc.web.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by marcel on 14-8-2014.
 */
@XmlRootElement(name = "user")
public class UserDto {
    private String username;
    private String password;

    public UserDto() {}
    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }
}
