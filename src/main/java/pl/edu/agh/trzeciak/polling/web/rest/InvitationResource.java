package pl.edu.agh.trzeciak.polling.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.edu.agh.trzeciak.polling.domain.Invitation;
import pl.edu.agh.trzeciak.polling.repository.InvitationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.trzeciak.polling.service.InvitationService;
import pl.edu.agh.trzeciak.polling.service.UserService;
import pl.edu.agh.trzeciak.polling.web.rest.dto.InvitationRequestDTO;
import pl.edu.agh.trzeciak.polling.web.rest.dto.InvitationResultDTO;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Invitation.
 */
@RestController
@RequestMapping("/app")
public class InvitationResource {

    private final Logger log = LoggerFactory.getLogger(InvitationResource.class);

    @Inject
    private InvitationRepository invitationRepository;

    @Inject
    private UserService userService;

    @Inject
    private InvitationService invitationService;

    /**
     * POST  /rest/invitations -> Create a new invitation.
     */
    @RequestMapping(value = "/rest/invitations",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public InvitationResultDTO create(@RequestBody InvitationRequestDTO invitation) {
        log.debug("REST request to save Invitation : {}", invitation);
        InvitationResultDTO invitationResultDTO = invitationService.invite(invitation.getEmails(), invitation.getPoll());

        log.debug("REST result to save Invitation : {}", invitationResultDTO);
        return invitationResultDTO;
    }

    /**
     * GET  /rest/invitations -> get all the invitations.
     */
    @RequestMapping(value = "/rest/invitations",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Invitation> getAll() {
        log.debug("REST request to get all Invitations");
        return invitationRepository.findAll();
    }

    /**
     * GET  /rest/invitations/:id -> get the "id" invitation.
     */
    @RequestMapping(value = "/rest/invitations/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Invitation> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Invitation : {}", id);
        Invitation invitation = invitationRepository.findOne(id);
        if (invitation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(invitation, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/invitations/:id -> delete the "id" invitation.
     */
    @RequestMapping(value = "/rest/invitations/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Invitation : {}", id);
        invitationRepository.delete(id);
    }
}
