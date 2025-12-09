package com.invoice.Invoice_management.config;

import com.invoice.Invoice_management.entity.Role;
import com.invoice.Invoice_management.entity.User;
import com.invoice.Invoice_management.repository.RoleRepository;
import com.invoice.Invoice_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {
    @Bean
    public CommandLineRunner initDatabase(UserRepository userRepository,
                                          RoleRepository roleRepository,
                                          PasswordEncoder passwordEncoder) {
        return args -> {
            if(userRepository.findAll().isEmpty()) {
                Role adminRole = roleRepository.findByRoleName("ADMIN");
                User admin = new User("admin", passwordEncoder.encode("admin123"), "admin@example.com", adminRole);
                userRepository.save(admin);
            }
        };
    }
}
