package pl.edu.agh.trzeciak.polling.repository;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Query;
import pl.edu.agh.trzeciak.polling.domain.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.agh.trzeciak.polling.domain.User;

import java.util.List;

/**
 * Spring Data JPA repository for the Access entity.
 */
public interface AccessRepository extends JpaRepository<Access, Long> {

    @Query("select a.user from Access a where a.poll.id = ?1")
    List<User> findUsersWithPollAccess(long pollId);

    @Query("select a.poll from Access a where a.user.login = ?1")
    List<User> findPollsForUser(String login);

    @Query("delete from Access a where a.poll.id = ?1")
    void deleteByPollId(Long pollId);
}
