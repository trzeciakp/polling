package pl.edu.agh.trzeciak.polling.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.trzeciak.polling.domain.Product;
import pl.edu.agh.trzeciak.polling.domain.Score;
import pl.edu.agh.trzeciak.polling.domain.User;
import pl.edu.agh.trzeciak.polling.repository.AccessRepository;
import pl.edu.agh.trzeciak.polling.repository.ProductRepository;
import pl.edu.agh.trzeciak.polling.repository.ScoreRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ScoreRepository scoreRepository;

    @Inject
    private AccessRepository accessRepository;

    public void createProduct(Product product) {
        Long pollId = product.getPoll().getId();
        List<Product> products = productRepository.findProductByPollId(pollId);
        productRepository.save(product);
        List<User> users = accessRepository.findUsersWithPollAccess(pollId);
        createScoresForProduct(users, product, products);
    }

    public void createScoresForProduct(List<User> users, Product product, List<Product> products) {
        List<Score> scores = new ArrayList<>();
        Product productA;
        Product productB;
        for (Product product1 : products) {
            if (product.getId() < product1.getId()) {
                productA = product;
                productB = product1;
            } else if (product.getId().equals(product1.getId())) {
                continue;
            } else {
                productA = product1;
                productB = product;
            }
            for (User user : users) {
                Score score = new Score();
                score.setProductA(productA);
                score.setProductB(productB);
                score.setUser(user);
                score.setValue(null);
                scores.add(score);
            }
        }
        scoreRepository.save(scores);
    }


    public void delete(Long id) {
        productRepository.delete(id);
    }

    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
