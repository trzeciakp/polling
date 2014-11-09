package pl.edu.agh.trzeciak.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.agh.trzeciak.polling.domain.Product;

import java.util.List;

/**
 * Spring Data JPA repository for the Product entity.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.poll.id = ?1")
    List<Product> findProductByPollId(Long pollId);
}
