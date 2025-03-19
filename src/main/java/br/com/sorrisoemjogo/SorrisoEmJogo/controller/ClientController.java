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

}
