package br.com.sorrisoemjogo.SorrisoEmJogo.controller;


import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/clinics")
public class ClinicController {

    private final ClinicRepository clinicRepository;

    public ClinicController(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("clinic", new Clinic());
        return "clinic-register";
    }

    @PostMapping("/register")
    public String registerClinic(@ModelAttribute Clinic clinic) {
        clinic.setScore(0);
        clinicRepository.save(clinic);
        return "redirect:/login";
    }
}
