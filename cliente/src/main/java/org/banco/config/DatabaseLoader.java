package org.banco.config;

import jakarta.annotation.PostConstruct;
import org.banco.model.User;
import org.banco.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class DatabaseLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void initDatabase() {
        // Crear usuarios
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword(passwordEncoder.encode("password1"));

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword(passwordEncoder.encode("password2"));

        // Guardar usuarios en la base de datos
        userRepository.save(user1);
        userRepository.save(user2);
    }
}