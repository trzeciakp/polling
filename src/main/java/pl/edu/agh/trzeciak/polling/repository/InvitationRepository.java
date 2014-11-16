package pl.edu.agh.trzeciak.polling.repository;

import pl.edu.agh.trzeciak.polling.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Invitation entity.
 */
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

}
