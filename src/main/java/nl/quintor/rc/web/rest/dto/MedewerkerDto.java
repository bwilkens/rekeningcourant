package nl.quintor.rc.web.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MedewerkerDto extends GebruikerDto {
    private Long medewerkerId;

    public Long getMedewerkerId() {
        return medewerkerId;
    }

    public void setMedewerkerId(Long medewerkerId) {
        this.medewerkerId = medewerkerId;
    }
}
