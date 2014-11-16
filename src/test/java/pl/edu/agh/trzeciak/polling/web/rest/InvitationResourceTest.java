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
import pl.edu.agh.trzeciak.polling.domain.Invitation;
import pl.edu.agh.trzeciak.polling.repository.InvitationRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InvitationResource REST controller.
 *
 * @see InvitationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class InvitationResourceTest {

    private static final String DEFAULT_EMAIL = "SAMPLE_TEXT";
    private static final String UPDATED_EMAIL = "UPDATED_TEXT";
    

    @Inject
    private InvitationRepository invitationRepository;

    private MockMvc restInvitationMockMvc;

    private Invitation invitation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InvitationResource invitationResource = new InvitationResource();
        ReflectionTestUtils.setField(invitationResource, "invitationRepository", invitationRepository);
        this.restInvitationMockMvc = MockMvcBuilders.standaloneSetup(invitationResource).build();
    }

    @Before
    public void initTest() {
        invitation = new Invitation();
        invitation.setEmail(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createInvitation() throws Exception {
        // Validate the database is empty
        assertThat(invitationRepository.findAll()).hasSize(0);

        // Create the Invitation
        restInvitationMockMvc.perform(post("/app/rest/invitations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invitation)))
                .andExpect(status().isOk());

        // Validate the Invitation in the database
        List<Invitation> invitations = invitationRepository.findAll();
        assertThat(invitations).hasSize(1);
        Invitation testInvitation = invitations.iterator().next();
        assertThat(testInvitation.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInvitations() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        // Get all the invitations
        restInvitationMockMvc.perform(get("/app/rest/invitations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(invitation.getId().intValue()))
                .andExpect(jsonPath("$.[0].email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getInvitation() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        // Get the invitation
        restInvitationMockMvc.perform(get("/app/rest/invitations/{id}", invitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(invitation.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInvitation() throws Exception {
        // Get the invitation
        restInvitationMockMvc.perform(get("/app/rest/invitations/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitation() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        // Update the invitation
        invitation.setEmail(UPDATED_EMAIL);
        restInvitationMockMvc.perform(post("/app/rest/invitations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(invitation)))
                .andExpect(status().isOk());

        // Validate the Invitation in the database
        List<Invitation> invitations = invitationRepository.findAll();
        assertThat(invitations).hasSize(1);
        Invitation testInvitation = invitations.iterator().next();
        assertThat(testInvitation.getEmail()).isEqualTo(UPDATED_EMAIL);;
    }

    @Test
    @Transactional
    public void deleteInvitation() throws Exception {
        // Initialize the database
        invitationRepository.saveAndFlush(invitation);

        // Get the invitation
        restInvitationMockMvc.perform(delete("/app/rest/invitations/{id}", invitation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Invitation> invitations = invitationRepository.findAll();
        assertThat(invitations).hasSize(0);
    }
}
