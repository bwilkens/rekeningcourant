package nl.quintor.rc.jms.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RejectDto {
	private String betaalopdrachtId;
    private String reason;
    private String description;

    public String getBetaalopdrachtId() {
        return betaalopdrachtId;
    }

    public void setBetaalopdrachtId(String betaalopdrachtId) {
        this.betaalopdrachtId = betaalopdrachtId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
