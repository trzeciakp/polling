package pl.edu.agh.trzeciak.polling.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.trzeciak.polling.domain.*;
import pl.edu.agh.trzeciak.polling.repository.ProductRepository;
import pl.edu.agh.trzeciak.polling.repository.ScoreRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScoreService {

    private final Logger log = LoggerFactory.getLogger(ScoreService.class);

    @Inject
    private ScoreRepository scoreRepository;

    @Inject
    private ProductRepository productRepository;

    public List<Score> getEmptyScoresForUser(User user, Poll poll) {
        return scoreRepository.findAllEmptyScores(user.getLogin(), poll.getId());
    }


    public void createScoresForAccesses(List<Access> accesses) {
        List<Score> scores = new ArrayList<>();
        for (Access access : accesses) {
            scores.addAll(createScoresForPoll(access.getUser(), access.getPoll()));
        }
        scoreRepository.save(scores);
    }

    private List<Score> createScoresForPoll(User user, Poll poll) {
        Long pollId = poll.getId();
        List<Product> products = productRepository.findProductByPollId(pollId);
        List<Score> scores = new ArrayList<>();
        Product productA;
        Product productB;
        for (Product product : products) {
            for (Product product1 : products) {
                if (product.getId() < product1.getId()) {
                    productA = product;
                    productB = product1;

                    Score score = new Score();
                    score.setProductA(productA);
                    score.setProductB(productB);
                    score.setUser(user);
                    score.setValue(null);
                    scores.add(score);
                }
            }
        }
        return scores;
    }
}
