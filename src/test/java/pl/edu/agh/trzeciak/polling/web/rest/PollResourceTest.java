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
import pl.edu.agh.trzeciak.polling.domain.Poll;
import pl.edu.agh.trzeciak.polling.repository.PollRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PollResource REST controller.
 *
 * @see PollResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PollResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_MAX_SCORE = 0;
    private static final Integer UPDATED_MAX_SCORE = 1;
    

    @Inject
    private PollRepository pollRepository;

    private MockMvc restPollMockMvc;

    private Poll poll;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PollResource pollResource = new PollResource();
        ReflectionTestUtils.setField(pollResource, "pollRepository", pollRepository);
        this.restPollMockMvc = MockMvcBuilders.standaloneSetup(pollResource).build();
    }

    @Before
    public void initTest() {
        poll = new Poll();
        poll.setName(DEFAULT_NAME);
        poll.setMaxScore(DEFAULT_MAX_SCORE);
    }

    @Test
    @Transactional
    public void createPoll() throws Exception {
        // Validate the database is empty
        assertThat(pollRepository.findAll()).hasSize(0);

        // Create the Poll
        restPollMockMvc.perform(post("/app/rest/polls")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(poll)))
                .andExpect(status().isOk());

        // Validate the Poll in the database
        List<Poll> polls = pollRepository.findAll();
        assertThat(polls).hasSize(1);
        Poll testPoll = polls.iterator().next();
        assertThat(testPoll.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPoll.getMaxScore()).isEqualTo(DEFAULT_MAX_SCORE);
    }

    @Test
    @Transactional
    public void getAllPolls() throws Exception {
        // Initialize the database
        pollRepository.saveAndFlush(poll);

        // Get all the polls
        restPollMockMvc.perform(get("/app/rest/polls"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(poll.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].maxScore").value(DEFAULT_MAX_SCORE));
    }

    @Test
    @Transactional
    public void getPoll() throws Exception {
        // Initialize the database
        pollRepository.saveAndFlush(poll);

        // Get the poll
        restPollMockMvc.perform(get("/app/rest/polls/{id}", poll.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(poll.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.maxScore").value(DEFAULT_MAX_SCORE));
    }

    @Test
    @Transactional
    public void getNonExistingPoll() throws Exception {
        // Get the poll
        restPollMockMvc.perform(get("/app/rest/polls/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoll() throws Exception {
        // Initialize the database
        pollRepository.saveAndFlush(poll);

        // Update the poll
        poll.setName(UPDATED_NAME);
        poll.setMaxScore(UPDATED_MAX_SCORE);
        restPollMockMvc.perform(post("/app/rest/polls")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(poll)))
                .andExpect(status().isOk());

        // Validate the Poll in the database
        List<Poll> polls = pollRepository.findAll();
        assertThat(polls).hasSize(1);
        Poll testPoll = polls.iterator().next();
        assertThat(testPoll.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPoll.getMaxScore()).isEqualTo(UPDATED_MAX_SCORE);;
    }

    @Test
    @Transactional
    public void deletePoll() throws Exception {
        // Initialize the database
        pollRepository.saveAndFlush(poll);

        // Get the poll
        restPollMockMvc.perform(delete("/app/rest/polls/{id}", poll.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Poll> polls = pollRepository.findAll();
        assertThat(polls).hasSize(0);
    }
}
