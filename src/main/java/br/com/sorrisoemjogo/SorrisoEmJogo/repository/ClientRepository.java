package br.com.sorrisoemjogo.SorrisoEmJogo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Client;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByClinic(Clinic clinic);
}
