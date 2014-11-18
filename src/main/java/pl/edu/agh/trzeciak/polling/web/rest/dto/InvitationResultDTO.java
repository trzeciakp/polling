package pl.edu.agh.trzeciak.polling.web.rest.dto;

import java.util.List;

public class InvitationResultDTO {
    private List<String> emails;

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public InvitationResultDTO() {
    }

    @Override
    public String toString() {
        return "InvitationResultDTO{" +
            "emails=" + emails +
            '}';
    }
}
