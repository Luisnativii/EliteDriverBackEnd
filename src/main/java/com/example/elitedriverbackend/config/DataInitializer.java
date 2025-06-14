package com.example.elitedriverbackend.config;



import com.example.elitedriverbackend.domain.entity.User;
import com.example.elitedriverbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            try {
                String email = "admin@example.com";

                userRepository.findByEmail(email).ifPresentOrElse(admin -> {
                    System.out.println("✅ Admin ya existe, no se modificó.");
                }, () -> {
                    User newAdmin = User.builder()
                            .firstName("Admin")
                            .lastName("Root")
                            .birthDate("1990, 1, 1")
                            .dui("00000000-0")
                            .phoneNumber("7000-0000")
                            .email(email)
                            .password(passwordEncoder.encode("adminadmin"))
                            .role("ADMIN")
                            .build();

                    userRepository.save(newAdmin);
                    System.out.println("✅ Admin creado correctamente.");
                });
            } catch (Exception e) {
                System.err.println("🔥 Error inicializando admin: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

}