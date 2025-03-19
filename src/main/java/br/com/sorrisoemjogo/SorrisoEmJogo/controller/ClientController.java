package br.com.sorrisoemjogo.SorrisoEmJogo.controller;

import java.util.List;


import br.com.sorrisoemjogo.SorrisoEmJogo.model.Client;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClientRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/new")
    public String showClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "client-form"; // Template: client-form.html
    }

    @PostMapping("/new")
    public String saveClient(@ModelAttribute Client client, HttpSession session) {
        // Recupera a clínica logada da sessão
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        client.setClinic(loggedClinic);
        clientService.saveClient(client);
        return "redirect:/clients/list";
    }

    @GetMapping("/list")
    public String listClients(Model model, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        List<Client> clients = clientRepository.findByClinic(loggedClinic);
        model.addAttribute("clients", clients);
        return "client-list"; // Deve corresponder a client-list.html
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        // Verifica se o cliente pertence à clínica logada
        if (!client.getClinic().getId().equals(loggedClinic.getId())) {
            return "redirect:/clients/list";
        }
        model.addAttribute("client", client);
        return "client-edit"; // Criar este template
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable("id") Long id, @ModelAttribute Client client, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));

        // Atualiza todos os campos
        existingClient.setName(client.getName());
        existingClient.setCpf(client.getCpf());
        existingClient.setIdade(client.getIdade());
        existingClient.setEmail(client.getEmail());
        existingClient.setProblemasOdontologicos(client.getProblemasOdontologicos());
        existingClient.setPlanoOdontologico(client.getPlanoOdontologico());

        clientRepository.save(existingClient);
        return "redirect:/clients/list";
    }


    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        // Verifica se o cliente pertence à clínica logada
        if (!client.getClinic().getId().equals(loggedClinic.getId())) {
            return "redirect:/clients/list";
        }
        clientRepository.delete(client);
        return "redirect:/clients/list";
    }


}
