package org.banco.comunicaciones.kafka;

import jakarta.mail.MessagingException;
import org.banco.comunicaciones.model.Email;
import org.banco.comunicaciones.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${kafka.topic.email}", groupId = "${kafka.group.id}")
    public void consume(Email email) {
        try {
            emailService.sendEmail(email);
        } catch (MessagingException | MailException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}