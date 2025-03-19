package br.com.sorrisoemjogo.SorrisoEmJogo.service;


import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Client;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClientRepository;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClinicRepository clinicRepository;

    public Client saveClient(Client client) {
        // Salva o cliente
        Client savedClient = clientRepository.save(client);

        // Atualiza a pontuação da clínica (10 pontos por cliente cadastrado)
        Clinic clinic = savedClient.getClinic();
        clinic.setScore(clinic.getScore() + 10);
        clinicRepository.save(clinic);

        return savedClient;
    }
}
