package br.com.sorrisoemjogo.SorrisoEmJogo.controller;

import java.util.List;


import br.com.sorrisoemjogo.SorrisoEmJogo.security.ClinicUserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;

    public AppointmentController(AppointmentService appointmentService,
                                 AppointmentRepository appointmentRepository,
                                 ClientRepository clientRepository) {
        this.appointmentService = appointmentService;
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/new")
    public String showAppointmentForm(Model model,
                                      @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        model.addAttribute("clinic", clinic);
        model.addAttribute("appointment", new Appointment());
        List<Client> clients = clientRepository.findByClinic(clinic);
        model.addAttribute("clients", clients);
        return "appointment-form";
    }

    @PostMapping("/new")
    public String saveAppointment(@ModelAttribute Appointment appointment,
                                  @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        // garante que o cliente exista e pertença à clínica
        Client client = clientRepository.findById(appointment.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        if (!client.getClinic().getId().equals(clinic.getId())) {
            return "redirect:/appointments/list";
        }
        appointment.setClient(client);
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments/list";
    }

    @GetMapping("/list")
    public String listAppointments(Model model,
                                   @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        List<Appointment> appointments = appointmentRepository.findByClient_Clinic(clinic);
        model.addAttribute("clinic", clinic);
        model.addAttribute("appointments", appointments);
        return "appointment-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditAppointmentForm(@PathVariable Long id,
                                          Model model,
                                          @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
        if (!appointment.getClient().getClinic().getId().equals(clinic.getId())) {
            return "redirect:/appointments/list";
        }
        model.addAttribute("clinic", clinic);
        model.addAttribute("appointment", appointment);
        model.addAttribute("clients", clientRepository.findByClinic(clinic));
        return "appointment-edit";
    }

    @PostMapping("/update/{id}")
    public String updateAppointment(@PathVariable Long id,
                                    @ModelAttribute Appointment appointmentForm,
                                    @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
        if (!existing.getClient().getClinic().getId().equals(clinic.getId())) {
            return "redirect:/appointments/list";
        }
        Client client = clientRepository.findById(appointmentForm.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        existing.setClient(client);
        existing.setAppointmentDateTime(appointmentForm.getAppointmentDateTime());
        existing.setProblemaPaciente(appointmentForm.getProblemaPaciente());
        appointmentRepository.save(existing);
        return "redirect:/appointments/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id,
                                    @AuthenticationPrincipal ClinicUserDetails userDetails) {
        Clinic clinic = userDetails.getClinic();
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + id));
        if (!appointment.getClient().getClinic().getId().equals(clinic.getId())) {
            return "redirect:/appointments/list";
        }
        appointmentRepository.delete(appointment);
        return "redirect:/appointments/list";
    }
}
