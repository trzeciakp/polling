package pl.edu.agh.trzeciak.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.agh.trzeciak.polling.domain.Invitation;

import java.util.List;

/**
 * Spring Data JPA repository for the Invitation entity.
 */
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    @Query("select i from Invitation i where i.poll.id = ?1")
    List<Invitation> findInvitationByPollId(long pollId);

    @Query("select i from Invitation i where i.email = ?1")
    List<Invitation> findInvitationByEmail(String email);
}
