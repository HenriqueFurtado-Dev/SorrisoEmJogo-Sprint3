package br.com.sorrisoemjogo.SorrisoEmJogo.controller;

import java.util.List;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Appointment;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Client;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.AppointmentRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClientRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.service.AppointmentService;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/new")
    public String showAppointmentForm(Model model, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        model.addAttribute("appointment", new Appointment());
        List<Client> clients = clientRepository.findByClinic(loggedClinic);
        model.addAttribute("clients", clients);
        return "appointment-form";
    }

    @PostMapping("/new")
    public String saveAppointment(@ModelAttribute Appointment appointment, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        Long clientId = appointment.getClient().getId();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        appointment.setClient(client);
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments/list";
    }

    @GetMapping("/list")
    public String listAppointments(Model model, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        List<Appointment> appointments = appointmentRepository.findAll();
        model.addAttribute("appointments", appointments);
        return "appointment-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditAppointmentForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
        model.addAttribute("appointment", appointment);
        List<Client> clients = clientRepository.findByClinic(loggedClinic);
        model.addAttribute("clients", clients);
        return "appointment-edit";
    }

    @PostMapping("/update/{id}")
    public String updateAppointment(@PathVariable("id") Long id, @ModelAttribute Appointment appointment, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));

        Long clientId = appointment.getClient().getId();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clientId));
        existingAppointment.setClient(client);
        existingAppointment.setAppointmentDateTime(appointment.getAppointmentDateTime());
        existingAppointment.setProblemaPaciente(appointment.getProblemaPaciente());
        appointmentRepository.save(existingAppointment);
        return "redirect:/appointments/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable("id") Long id, HttpSession session) {
        Clinic loggedClinic = (Clinic) session.getAttribute("loggedClinic");
        if (loggedClinic == null) {
            return "redirect:/login";
        }
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
        appointmentRepository.delete(appointment);
        return "redirect:/appointments/list";
    }
}
