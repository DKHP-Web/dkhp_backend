package dkhpweb.dkhp_backend.utils;

import dkhpweb.dkhp_backend.exceptions.SendEmailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailUtil {
    private final JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    String fromAddress;

    public void sendMail(String to, String subject, String content) {
        try{
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
        }
        catch(Exception e){
            log.error("Unable to send email: {}", e.getMessage());
            throw new SendEmailException("Sending mail errors");
        }
    }
}
