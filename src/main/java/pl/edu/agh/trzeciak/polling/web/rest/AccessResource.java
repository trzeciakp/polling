package pl.edu.agh.trzeciak.polling.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.trzeciak.polling.domain.Access;
import pl.edu.agh.trzeciak.polling.domain.User;
import pl.edu.agh.trzeciak.polling.repository.AccessRepository;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Access.
 */
@RestController
@RequestMapping("/app")
public class AccessResource {

    private final Logger log = LoggerFactory.getLogger(AccessResource.class);

    @Inject
    private AccessRepository accessRepository;

    /**
     * POST  /rest/accesss -> Create a new access.
     */
    @RequestMapping(value = "/rest/accesss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Access access) {
        log.debug("REST request to save Access : {}", access);
        accessRepository.save(access);
    }

    /**
     * GET  /rest/accesss -> get all the accesss.
     */
    @RequestMapping(value = "/rest/accesss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Access> getAll() {
        log.debug("REST request to get all Accesss");
        return accessRepository.findAll();
    }

    /**
     * GET  /rest/accesss/:id -> get the "id" access.
     */
    @RequestMapping(value = "/rest/accesss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Access> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Access : {}", id);
        Access access = accessRepository.findOne(id);
        if (access == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(access, HttpStatus.OK);
    }

    /**
     * GET  /rest/accesss/:id -> get the "id" access.
     */
    @RequestMapping(value = "/rest/accesss/poll/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<User>> getUsersWithPollAccess(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Users with poll Access : {}", id);
        List<User> users = accessRepository.findUsersWithPollAccess(id);
        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/accesss/:id -> delete the "id" access.
     */
    @RequestMapping(value = "/rest/accesss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Access : {}", id);
        accessRepository.delete(id);
    }
}
