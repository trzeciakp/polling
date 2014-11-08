package pl.edu.agh.trzeciak.polling.repository;

import pl.edu.agh.trzeciak.polling.domain.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Poll entity.
 */
public interface PollRepository extends JpaRepository<Poll, Long> {

}
