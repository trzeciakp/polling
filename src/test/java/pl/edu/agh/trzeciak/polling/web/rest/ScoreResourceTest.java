package pl.edu.agh.trzeciak.polling.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import pl.edu.agh.trzeciak.polling.Application;
import pl.edu.agh.trzeciak.polling.domain.Score;
import pl.edu.agh.trzeciak.polling.repository.ScoreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ScoreResource REST controller.
 *
 * @see ScoreResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ScoreResourceTest {

    private static final Integer DEFAULT_VALUE = 0;
    private static final Integer UPDATED_VALUE = 1;
    

    @Inject
    private ScoreRepository scoreRepository;

    private MockMvc restScoreMockMvc;

    private Score score;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScoreResource scoreResource = new ScoreResource();
        ReflectionTestUtils.setField(scoreResource, "scoreRepository", scoreRepository);
        this.restScoreMockMvc = MockMvcBuilders.standaloneSetup(scoreResource).build();
    }

    @Before
    public void initTest() {
        score = new Score();
        score.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createScore() throws Exception {
        // Validate the database is empty
        assertThat(scoreRepository.findAll()).hasSize(0);

        // Create the Score
        restScoreMockMvc.perform(post("/app/rest/scores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(score)))
                .andExpect(status().isOk());

        // Validate the Score in the database
        List<Score> scores = scoreRepository.findAll();
        assertThat(scores).hasSize(1);
        Score testScore = scores.iterator().next();
        assertThat(testScore.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllScores() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get all the scores
        restScoreMockMvc.perform(get("/app/rest/scores"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(score.getId().intValue()))
                .andExpect(jsonPath("$.[0].value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get the score
        restScoreMockMvc.perform(get("/app/rest/scores/{id}", score.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(score.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingScore() throws Exception {
        // Get the score
        restScoreMockMvc.perform(get("/app/rest/scores/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Update the score
        score.setValue(UPDATED_VALUE);
        restScoreMockMvc.perform(post("/app/rest/scores")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(score)))
                .andExpect(status().isOk());

        // Validate the Score in the database
        List<Score> scores = scoreRepository.findAll();
        assertThat(scores).hasSize(1);
        Score testScore = scores.iterator().next();
        assertThat(testScore.getValue()).isEqualTo(UPDATED_VALUE);;
    }

    @Test
    @Transactional
    public void deleteScore() throws Exception {
        // Initialize the database
        scoreRepository.saveAndFlush(score);

        // Get the score
        restScoreMockMvc.perform(delete("/app/rest/scores/{id}", score.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Score> scores = scoreRepository.findAll();
        assertThat(scores).hasSize(0);
    }
}
