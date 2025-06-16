package com.example.elitedriverbackend.config;

import com.example.elitedriverbackend.domain.entity.CarType;
import com.example.elitedriverbackend.domain.entity.User;
import com.example.elitedriverbackend.repositories.CarTypeRepository;
import com.example.elitedriverbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final CarTypeRepository carTypeRepository;  // ← inyectado
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // 1) Crear usuario ADMIN si no existe
            String adminEmail = "admin@example.com";
            userRepository.findByEmail(adminEmail).ifPresentOrElse(u -> {
                System.out.println("✅ Admin ya existe");
            }, () -> {
                User admin = User.builder()
                        // NO seteamos el ID, lo genera JPA
                        .firstName("Admin")
                        .lastName("Root")
                        .birthDate("1990-01-01")
                        .dui("00000000-0")
                        .phoneNumber("7000-0000")
                        .email(adminEmail)
                        .password(passwordEncoder.encode("adminadmin"))
                        .role("ADMIN")
                        .build();
                userRepository.save(admin);
                System.out.println("✅ Admin creado");
            });

            // 2) Sembrar CarType: PickUps, Sedan, SUV

            // Verificamos si ya existen los tipos de auto
            List<String> tipos = List.of("PickUps", "Sedan", "SUV");
            tipos.forEach(tipoNombre ->
                    carTypeRepository.findByType(tipoNombre).ifPresentOrElse(ct -> {
                        System.out.println("✅ CarType '" + tipoNombre + "' ya existe");
                    }, () -> {
                        CarType nuevo = CarType.builder()

                                .type(tipoNombre)
                                .build();
                        carTypeRepository.save(nuevo);
                        System.out.println("✅ CarType '" + tipoNombre + "' creado");
                    })
            );
        };
    }
}

//si lo ve no lo toque solo par conocedores
