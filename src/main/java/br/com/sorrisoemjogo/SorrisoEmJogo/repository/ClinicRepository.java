package br.com.sorrisoemjogo.SorrisoEmJogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;

import java.util.Optional;   // <-- importe java.util.Optional

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    // agora retorna Optional
    Optional<Clinic> findByEmail(String email);

    // útil para o CommandLineRunner que cria o admin
    boolean existsByEmail(String email);
}
