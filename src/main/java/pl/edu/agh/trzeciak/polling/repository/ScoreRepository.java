package pl.edu.agh.trzeciak.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.agh.trzeciak.polling.domain.Score;

import java.util.List;

/**
 * Spring Data JPA repository for the Score entity.
 */
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("select s from Score s where s.user.login = ?1 and s.productA.poll.id = ?2 and s.value is null")
    List<Score> findAllEmptyScores(String login, Long id);

    @Query("select count(s) from Score s where s.user.login = ?1 and s.productA.poll.id = ?2")
    Integer countAllUserScoresForPoll(String login, Long id);

    @Query("select count(s) from Score s where s.user.login = ?1 and s.productA.poll.id = ?2 and s.value is null")
    Integer countEmptyUserScoresForPoll(String login, Long id);
}
