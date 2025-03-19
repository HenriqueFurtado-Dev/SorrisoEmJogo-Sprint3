package br.com.sorrisoemjogo.SorrisoEmJogo.service;

import br.com.sorrisoemjogo.SorrisoEmJogo.model.Appointment;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Client;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.AppointmentRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClinicRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Appointment saveAppointment(Appointment appointment) {
        // Recupera o ID do cliente enviado pelo formulário
        Long clientId = appointment.getClient().getId();

        // Carrega o cliente completo do banco de dados
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Define o cliente completo no agendamento
        appointment.setClient(client);

        // Salva o agendamento
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Atualiza a pontuação da clínica (5 pontos por agendamento)
        Clinic clinic = client.getClinic();
        if (clinic != null) {
            clinic.setScore(clinic.getScore() + 5);
            clinicRepository.save(clinic);
        } else {
            throw new RuntimeException("Cliente não possui clínica associada");
        }

        return savedAppointment;
    }
}
