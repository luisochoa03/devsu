package org.banco.comunicaciones.kafka;

import org.banco.comunicaciones.model.Email;
import org.banco.comunicaciones.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.mail.MessagingException;

import static org.mockito.Mockito.verify;

@SpringBootTest
class EmailConsumerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailConsumer emailConsumer;

    @Test
    public void testConsume() throws MessagingException {
        Email email = new Email("prueba@gmail.com", "Prueba", "Hola Mundo",null,"prueba.txt");
        emailConsumer.consume(email);

        verify(emailService).sendEmail(email);
    }
}