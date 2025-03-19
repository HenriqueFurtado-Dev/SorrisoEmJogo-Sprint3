package br.com.sorrisoemjogo.SorrisoEmJogo.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Client;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByClient(Client client);
}
