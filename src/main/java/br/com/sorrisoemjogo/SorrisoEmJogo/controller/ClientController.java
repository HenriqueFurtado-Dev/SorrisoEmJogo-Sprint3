package br.com.sorrisoemjogo.SorrisoEmJogo.controller;

import java.util.List;


import br.com.sorrisoemjogo.SorrisoEmJogo.model.Client;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClientRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.security.ClinicUserDetails;
import br.com.sorrisoemjogo.SorrisoEmJogo.service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    public ClientController(ClientService clientService,
                            ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/new")
    public String showClientForm(Model model,
                                 @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        model.addAttribute("clinic", clinic);
        model.addAttribute("client", new Client());
        return "client-form";
    }

    @PostMapping("/new")
    public String saveClient(@ModelAttribute Client client,
                             @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        client.setClinic(clinic);
        clientService.saveClient(client);
        return "redirect:/clients/list";
    }

    @GetMapping("/list")
    public String listClients(Model model,
                              @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        List<Client> clients = clientRepository.findByClinic(clinic);
        model.addAttribute("clinic", clinic);
        model.addAttribute("clients", clients);
        return "client-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model,
                               @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        if (!client.getClinic().getId().equals(clinic.getId())) {
            return "redirect:/clients/list";
        }
        model.addAttribute("clinic", clinic);
        model.addAttribute("client", client);
        return "client-edit";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable Long id,
                               @ModelAttribute Client clientForm,
                               @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        if (!existingClient.getClinic().getId().equals(clinic.getId())) {
            return "redirect:/clients/list";
        }

        existingClient.setName(clientForm.getName());
        existingClient.setCpf(clientForm.getCpf());
        existingClient.setIdade(clientForm.getIdade());
        existingClient.setEmail(clientForm.getEmail());
        existingClient.setProblemasOdontologicos(clientForm.getProblemasOdontologicos());
        existingClient.setPlanoOdontologico(clientForm.getPlanoOdontologico());

        clientRepository.save(existingClient);
        return "redirect:/clients/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable Long id,
                               @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        if (!client.getClinic().getId().equals(clinic.getId())) {
            return "redirect:/clients/list";
        }
        clientRepository.delete(client);
        return "redirect:/clients/list";
    }
}
