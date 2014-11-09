package pl.edu.agh.trzeciak.polling.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.trzeciak.polling.domain.Poll;
import pl.edu.agh.trzeciak.polling.domain.Score;
import pl.edu.agh.trzeciak.polling.domain.User;
import pl.edu.agh.trzeciak.polling.repository.ScoreRepository;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class ScoreService {

    private final Logger log = LoggerFactory.getLogger(ScoreService.class);

    @Inject
    private ScoreRepository scoreRepository;

    public List<Score> getEmptyScoresForUser(User user, Poll poll) {
        return scoreRepository.findAllEmptyScores(user.getLogin(), poll.getId());
    }

}
