package pl.edu.agh.trzeciak.polling.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.trzeciak.polling.domain.Access;
import pl.edu.agh.trzeciak.polling.domain.Poll;
import pl.edu.agh.trzeciak.polling.domain.Product;
import pl.edu.agh.trzeciak.polling.domain.User;
import pl.edu.agh.trzeciak.polling.repository.AccessRepository;
import pl.edu.agh.trzeciak.polling.repository.PollRepository;
import pl.edu.agh.trzeciak.polling.repository.ProductRepository;
import pl.edu.agh.trzeciak.polling.repository.UserRepository;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional
public class PollService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private AccessRepository accessRepository;

    @Inject
    private PollRepository pollRepository;

    @Inject
    private ProductRepository productRepository;

    private final Logger log = LoggerFactory.getLogger(PollService.class);

    public void createPoll(Poll poll) {
        User user = userRepository.getOne(poll.getUser().getLogin());
        pollRepository.save(poll);
        Access access = new Access();
        access.setPoll(poll);
        access.setUser(user);
        accessRepository.save(access);
    }

    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    public Poll findOne(Long id) {
        return pollRepository.findOne(id);
    }

    public void delete(Long id) {
        accessRepository.deleteByPollId(id);
        pollRepository.delete(id);
    }

    public List<Product> findPollProductsList(Long pollId) {
        return productRepository.findProductByPollId(pollId);
    }
}
