package pl.edu.agh.trzeciak.polling.repository;

import pl.edu.agh.trzeciak.polling.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
