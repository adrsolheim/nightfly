package no.adrsolheim.nightfly.service;

import no.adrsolheim.nightfly.exception.NightflyException;
import no.adrsolheim.nightfly.model.NotificationMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Async
    void sendMail(NotificationMail notificationMail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("noreply@adrsolheim.no");
            messageHelper.setTo(notificationMail.getRecipient());
            messageHelper.setSubject(notificationMail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationMail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            //log.info("Activation mail sent!");
        } catch (MailException e) {
            //log.error("Exception occured when send mail",e);
            throw new NightflyException("Exception occured when sending mail to " + notificationMail.getRecipient(), e);
        }
    }
}
