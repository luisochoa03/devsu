package org.banco.comunicaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MailProperties.class)
public class ComunicacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComunicacionesApplication.class, args);
	}

}
