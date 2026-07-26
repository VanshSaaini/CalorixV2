package com.calorix.backend.config;

import com.calorix.backend.entity.Role;
import com.calorix.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Seeds the role required by self-service registration on a new database. */
@Configuration
@RequiredArgsConstructor
public class DatabaseBootstrap {

    private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner seedDefaultRoles() {
        return args -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(Role.builder().name("ROLE_USER").build());
            }
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
            }
        };
    }
}
