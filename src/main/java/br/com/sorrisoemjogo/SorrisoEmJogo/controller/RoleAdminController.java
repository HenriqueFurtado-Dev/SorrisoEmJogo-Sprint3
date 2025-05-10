package br.com.sorrisoemjogo.SorrisoEmJogo.controller;

import br.com.sorrisoemjogo.SorrisoEmJogo.model.*;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/roles")
@PreAuthorize("hasRole('ADMIN')")   // só ADMIN acessa tudo aqui
public class RoleAdminController {

    @Autowired private ClinicRepository clinicRepo;
    @Autowired private RoleRepository   roleRepo;

    // lista clínicas e suas roles
    @GetMapping
    public String list(Model model) {
        model.addAttribute("clinics", clinicRepo.findAll());
        model.addAttribute("allRoles", roleRepo.findAll());
        return "role-admin";          // template Thymeleaf
    }

    // adiciona ou remove role em uma clínica
    @PostMapping("/toggle")
    public String toggleRole(@RequestParam Long clinicId,
                             @RequestParam Long roleId) {

        Clinic clinic = clinicRepo.findById(clinicId).orElseThrow();
        Role   role   = roleRepo.findById(roleId).orElseThrow();

        if (clinic.getRoles().contains(role))
            clinic.getRoles().remove(role);
        else
            clinic.getRoles().add(role);

        clinicRepo.save(clinic);
        return "redirect:/admin/roles";
    }
}
