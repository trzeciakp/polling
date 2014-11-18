package pl.edu.agh.trzeciak.polling.web.rest.dto;

import pl.edu.agh.trzeciak.polling.domain.Poll;

import java.util.List;

public class InvitationRequestDTO {
    private List<String> emails;
    private Poll poll;

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public InvitationRequestDTO() {
    }

    public InvitationRequestDTO(List<String> emails, Poll poll) {
        this.emails = emails;
        this.poll = poll;
    }
}
