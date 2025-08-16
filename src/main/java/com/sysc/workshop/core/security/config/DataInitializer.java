package com.sysc.workshop.core.security.config;

import com.sysc.workshop.core.role.Role;
import com.sysc.workshop.core.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepo;

    @Override
    public void run(String... args) {
        if (roleRepo.findByName("ROLE_USER").isEmpty()) {
            roleRepo.save(new Role("ROLE_USER"));
        }
        if (roleRepo.findByName("ROLE_ADMIN").isEmpty()) {
            roleRepo.save(new Role("ROLE_ADMIN"));
        }
    }
}
