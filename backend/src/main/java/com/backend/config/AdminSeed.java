package com.backend.config;

import com.backend.model.entity.User;
import com.backend.model.enums.Role;
import com.backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminSeed {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeed(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createAdmin() {
        if (userRepository.findByEmail("admin@admin.com").isEmpty()) {

            User admin = new User();
            admin.setName("Administrator");
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("adminadmin"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }
    }
}
