package br.com.sorrisoemjogo.SorrisoEmJogo.repository;


import java.util.List;

import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Client;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByClient(Client client);

    // Novo: busca todos os agendamentos cuja appointment.client.clinic == clinic
    List<Appointment> findByClient_Clinic(Clinic clinic);
}
