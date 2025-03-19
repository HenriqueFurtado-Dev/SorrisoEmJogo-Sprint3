package br.com.sorrisoemjogo.SorrisoEmJogo.controller;


import java.util.List;


import br.com.sorrisoemjogo.SorrisoEmJogo.model.Appointment;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Client;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.AppointmentRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClientRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.service.AppointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/new")
    public String showAppointmentForm(Model model, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        model.addAttribute("appointment", new Appointment());
        // Para que a clínica possa escolher entre seus clientes cadastrados:
        List<Client> clients = clientRepository.findByClinic(loggedClinic);
        model.addAttribute("clients", clients);
        return "appointment-form"; // Template: appointment-form.html
    }

    @PostMapping("/new")
    public String saveAppointment(@ModelAttribute Appointment appointment, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments/list";
    }

    @GetMapping("/list")
    public String listAppointments(Model model, HttpSession session) {
        // Aqui você pode implementar um filtro para mostrar apenas os agendamentos dos clientes da clínica logada
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        // Exemplo simplificado: buscar todos os agendamentos
        List<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "appointment-list"; // Template: appointment-list.html
    }
}
