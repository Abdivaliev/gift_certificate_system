package com.epam.esm;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminGenerator implements CommandLineRunner {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @Value(value = "${spring.jpa.hibernate.ddl-auto}")
    private String ddl;
    @Override
    public void run(String... args) throws Exception {
        if (ddl.equalsIgnoreCase("create") || ddl.equalsIgnoreCase("create-drop")) {
            User user = new User();
            user.setName("administrator");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRole(Role.ADMIN);
            userRepo.save(user);
        }
    }
}