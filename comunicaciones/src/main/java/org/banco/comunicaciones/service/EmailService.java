package org.banco.comunicaciones.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.stereotype.Service;
import org.banco.comunicaciones.model.Email;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(Email email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(email.getBody(), true);

        if (email.getAttachment() != null) {
            ByteArrayDataSource dataSource = new ByteArrayDataSource(email.getAttachment(), "application/octet-stream");
            helper.addAttachment(email.getNameAttachment(),dataSource);
        }

        mailSender.send(message);
    }
}