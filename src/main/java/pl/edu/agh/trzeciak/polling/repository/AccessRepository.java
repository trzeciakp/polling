package pl.edu.agh.trzeciak.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.agh.trzeciak.polling.domain.Access;
import pl.edu.agh.trzeciak.polling.domain.Poll;
import pl.edu.agh.trzeciak.polling.domain.User;

import java.util.List;

/**
 * Spring Data JPA repository for the Access entity.
 */
public interface AccessRepository extends JpaRepository<Access, Long> {

    @Query("select a.user from Access a where a.poll.id = ?1")
    List<User> findUsersWithPollAccess(long pollId);

    @Query("select a.poll from Access a where a.user.login = ?1")
    List<Poll> findPollsForUser(String login);

    @Query("select a from Access a where a.user.login = ?1 and a.poll.id = ?2")
    Access hasAccess(String login, long pollId);

    @Query("delete from Access a where a.poll.id = ?1")
    void deleteByPollId(Long pollId);
}
