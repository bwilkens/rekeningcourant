package nl.quintor.rc.jms.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusUpdateDto {
	private String betaalopdrachtId;
    private String status;
    private String omschrijving;

    public String getBetaalopdrachtId() {
        return betaalopdrachtId;
    }

    public void setBetaalopdrachtId(String betaalopdrachtId) {
        this.betaalopdrachtId = betaalopdrachtId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}
