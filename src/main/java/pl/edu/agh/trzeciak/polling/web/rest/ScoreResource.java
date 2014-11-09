package pl.edu.agh.trzeciak.polling.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.edu.agh.trzeciak.polling.domain.Score;
import pl.edu.agh.trzeciak.polling.repository.ScoreRepository;
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
 * REST controller for managing Score.
 */
@RestController
@RequestMapping("/app")
public class ScoreResource {

    private final Logger log = LoggerFactory.getLogger(ScoreResource.class);

    @Inject
    private ScoreRepository scoreRepository;

    /**
     * POST  /rest/scores -> Create a new score.
     */
    @RequestMapping(value = "/rest/scores",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Score score) {
        log.debug("REST request to save Score : {}", score);
        scoreRepository.save(score);
    }

    /**
     * GET  /rest/scores -> get all the scores.
     */
    @RequestMapping(value = "/rest/scores",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Score> getAll() {
        log.debug("REST request to get all Scores");
        return scoreRepository.findAll();
    }

    /**
     * GET  /rest/scores/:id -> get the "id" score.
     */
    @RequestMapping(value = "/rest/scores/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Score> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Score : {}", id);
        Score score = scoreRepository.findOne(id);
        if (score == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/scores/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Score>> getEmptyScores(@PathVariable String user, @PathVariable Long pollId, HttpServletResponse response) {
        log.debug("REST request to get Score, user: {}, pollId : {}", user, pollId);
        List<Score> scores = scoreRepository.findAllEmptyScores(user, pollId);
        if (scores == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/scores/:id -> delete the "id" score.
     */
    @RequestMapping(value = "/rest/scores/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Score : {}", id);
        scoreRepository.delete(id);
    }
}
