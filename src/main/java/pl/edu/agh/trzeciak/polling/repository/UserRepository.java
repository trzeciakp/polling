package pl.edu.agh.trzeciak.polling.repository;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.agh.trzeciak.polling.domain.User;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u from User u where u.activationKey = ?1")
    User getUserByActivationKey(String activationKey);

    @Query("select u from User u where u.activated = false and u.createdDate > ?1")
    List<User> findNotActivatedUsersByCreationDateBefore(DateTime dateTime);

    @Query("select u from User u where u.email in ?1")
    List<User> findAllUsersByEmails(Set<String> emails);

}
