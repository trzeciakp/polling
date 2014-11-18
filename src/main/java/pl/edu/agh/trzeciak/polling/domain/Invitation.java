package pl.edu.agh.trzeciak.polling.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Invitation.
 */
@Entity
@Table(name = "T_INVITATION", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "poll_id"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Invitation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "email")
    private String email;

    @ManyToOne
    private Poll poll;

    public Invitation() {
    }

    public Invitation(String email, Poll poll) {
        this.email = email;
        this.poll = poll;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invitation that = (Invitation) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (poll != null ? !poll.equals(that.poll) : that.poll != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (poll != null ? poll.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "id=" + id +
                ", email='" + email + "'" +
                '}';
    }
}
