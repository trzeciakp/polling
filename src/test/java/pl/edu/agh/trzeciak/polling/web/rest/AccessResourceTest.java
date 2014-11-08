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
import pl.edu.agh.trzeciak.polling.domain.Access;
import pl.edu.agh.trzeciak.polling.repository.AccessRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AccessResource REST controller.
 *
 * @see AccessResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AccessResourceTest {


    @Inject
    private AccessRepository accessRepository;

    private MockMvc restAccessMockMvc;

    private Access access;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccessResource accessResource = new AccessResource();
        ReflectionTestUtils.setField(accessResource, "accessRepository", accessRepository);
        this.restAccessMockMvc = MockMvcBuilders.standaloneSetup(accessResource).build();
    }

    @Before
    public void initTest() {
        access = new Access();
    }

    @Test
    @Transactional
    public void createAccess() throws Exception {
        // Validate the database is empty
        assertThat(accessRepository.findAll()).hasSize(0);

        // Create the Access
        restAccessMockMvc.perform(post("/app/rest/accesss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(access)))
                .andExpect(status().isOk());

        // Validate the Access in the database
        List<Access> accesss = accessRepository.findAll();
        assertThat(accesss).hasSize(1);
        Access testAccess = accesss.iterator().next();
    }

    @Test
    @Transactional
    public void getAllAccesss() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get all the accesss
        restAccessMockMvc.perform(get("/app/rest/accesss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(access.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAccess() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get the access
        restAccessMockMvc.perform(get("/app/rest/accesss/{id}", access.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(access.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccess() throws Exception {
        // Get the access
        restAccessMockMvc.perform(get("/app/rest/accesss/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccess() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Update the access
        restAccessMockMvc.perform(post("/app/rest/accesss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(access)))
                .andExpect(status().isOk());

        // Validate the Access in the database
        List<Access> accesss = accessRepository.findAll();
        assertThat(accesss).hasSize(1);
        Access testAccess = accesss.iterator().next();;
    }

    @Test
    @Transactional
    public void deleteAccess() throws Exception {
        // Initialize the database
        accessRepository.saveAndFlush(access);

        // Get the access
        restAccessMockMvc.perform(delete("/app/rest/accesss/{id}", access.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Access> accesss = accessRepository.findAll();
        assertThat(accesss).hasSize(0);
    }
}
