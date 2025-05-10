package br.com.sorrisoemjogo.SorrisoEmJogo.config;

import br.com.sorrisoemjogo.SorrisoEmJogo.model.*;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(RoleRepository roleRepo,
                           ClinicRepository clinicRepo,
                           PasswordEncoder encoder) {

        return args -> {
            // cria role ADMIN se não existir
            Role adminRole = roleRepo.findByName("ROLE_ADMIN");
            if (adminRole == null)
                adminRole = roleRepo.save(new Role(null, "ROLE_ADMIN"));

            // cria role USER se desejar
            Role userRole = roleRepo.findByName("ROLE_USER");
            if (userRole == null)
                userRole = roleRepo.save(new Role(null, "ROLE_USER"));

            // cria usuário admin caso não exista
            if (!clinicRepo.existsByEmail("admin@sorriso.com")) {
                Clinic admin = new Clinic();
                admin.setName("Administrador");
                admin.setEmail("admin@sorriso.com");
                admin.setPassword(encoder.encode("admin123"));
                admin.getRoles().add(adminRole);
                clinicRepo.save(admin);
            }
        };
    }
}
