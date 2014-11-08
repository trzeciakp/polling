package pl.edu.agh.trzeciak.polling.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.edu.agh.trzeciak.polling.domain.Poll;
import pl.edu.agh.trzeciak.polling.repository.PollRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Poll.
 */
@RestController
@RequestMapping("/app")
public class PollResource {

    private final Logger log = LoggerFactory.getLogger(PollResource.class);

    @Inject
    private PollRepository pollRepository;

    /**
     * POST  /rest/polls -> Create a new poll.
     */
    @RequestMapping(value = "/rest/polls",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Poll poll) {
        log.debug("REST request to save Poll : {}", poll);
        pollRepository.save(poll);
    }

    /**
     * GET  /rest/polls -> get all the polls.
     */
    @RequestMapping(value = "/rest/polls",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Poll> getAll() {
        log.debug("REST request to get all Polls");
        return pollRepository.findAll();
    }

    /**
     * GET  /rest/polls/:id -> get the "id" poll.
     */
    @RequestMapping(value = "/rest/polls/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Poll> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Poll : {}", id);
        Poll poll = pollRepository.findOne(id);
        if (poll == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(poll, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/polls/:id -> delete the "id" poll.
     */
    @RequestMapping(value = "/rest/polls/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Poll : {}", id);
        pollRepository.delete(id);
    }
}
