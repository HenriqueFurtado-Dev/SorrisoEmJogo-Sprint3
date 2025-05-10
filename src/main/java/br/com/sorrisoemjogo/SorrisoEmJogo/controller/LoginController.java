package br.com.sorrisoemjogo.SorrisoEmJogo.controller;

import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import br.com.sorrisoemjogo.SorrisoEmJogo.security.ClinicUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // GET /login – só exibe a página
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // GET /dashboard
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // pega o usuário logado do SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Clinic clinic = ((ClinicUserDetails) auth.getPrincipal()).getClinic();

        model.addAttribute("clinic", clinic);
        return "dashboard";
    }
}
