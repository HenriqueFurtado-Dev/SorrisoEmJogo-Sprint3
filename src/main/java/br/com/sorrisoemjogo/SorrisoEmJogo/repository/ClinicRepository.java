package br.com.sorrisoemjogo.SorrisoEmJogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    Clinic findByEmail(String email);
}