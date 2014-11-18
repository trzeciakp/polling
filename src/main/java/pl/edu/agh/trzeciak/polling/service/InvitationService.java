package pl.edu.agh.trzeciak.polling.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring4.SpringTemplateEngine;
import pl.edu.agh.trzeciak.polling.domain.Access;
import pl.edu.agh.trzeciak.polling.domain.Invitation;
import pl.edu.agh.trzeciak.polling.domain.Poll;
import pl.edu.agh.trzeciak.polling.domain.User;
import pl.edu.agh.trzeciak.polling.repository.AccessRepository;
import pl.edu.agh.trzeciak.polling.repository.InvitationRepository;
import pl.edu.agh.trzeciak.polling.repository.UserRepository;
import pl.edu.agh.trzeciak.polling.web.rest.dto.InvitationResultDTO;

import javax.inject.Inject;
import java.util.*;

@Service
@Transactional
public class InvitationService {

    private final Logger log = LoggerFactory.getLogger(InvitationService.class);

    @Inject
    private InvitationRepository invitationRepository;

    @Inject
    private MailService mailService;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AccessRepository accessRepository;


    @Inject
    private ApplicationContext applicationContext;

    @Inject
    private SpringTemplateEngine templateEngine;

    public InvitationResultDTO invite(List<String> emails, Poll poll) {
        Set<String> emailsLC = new HashSet<>();
        for (String email : emails) {
            emailsLC.add(email.toLowerCase());
        }
        List<User> users = userRepository.findAllUsersByEmails(emailsLC);
        Set<User> usersWithAccess = new HashSet<User>(accessRepository.findUsersWithPollAccess(poll.getId()));
        List<Access> accessesToAdd = new ArrayList<>();

        for (User user : users) {
            if (!usersWithAccess.contains(user)) {
                accessesToAdd.add(new Access(poll, user));
            }
            emailsLC.remove(user.getEmail());
        }

        Set<Invitation> existingInvitations = new HashSet<>(invitationRepository.findInvitationByPollId(poll.getId()));
        List<Invitation> invitationsToAdd = new ArrayList<>();
        for (String email : emailsLC) {
            Invitation invitation = new Invitation(email, poll);
            if (!existingInvitations.contains(invitation)) {
                invitationsToAdd.add(invitation);
                Locale locale = Locale.forLanguageTag("en");
                String content = createHtmlContentFromTemplate(poll, locale);
                mailService.sendInvitationEmail(email, content, locale);
            }
        }
        invitationRepository.save(invitationsToAdd);
        accessRepository.save(accessesToAdd);
        InvitationResultDTO result = new InvitationResultDTO();
        result.setEmails(new ArrayList<String>(emailsLC));

        return result;
    }

    private String createHtmlContentFromTemplate(final Poll poll, final Locale locale) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("poll", poll);
        String baseUrl = applicationContext.getEnvironment().getProperty("spring.mail.baseUrl");
        variables.put("baseUrl", baseUrl);
        IContext context = new Context(locale, variables);
        return templateEngine.process("invitationEmail", context);
    }
}
