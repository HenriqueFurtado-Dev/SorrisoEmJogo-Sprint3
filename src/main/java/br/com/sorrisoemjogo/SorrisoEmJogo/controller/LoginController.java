package br.com.sorrisoemjogo.SorrisoEmJogo.controller;


import br.com.sorrisoemjogo.SorrisoEmJogo.model.Clinic;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.ClinicRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private ClinicRepository clinicRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Template Thymeleaf: login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Clinic clinic = clinicRepository.findByEmail(email);
        if (clinic != null && clinic.getPassword().equals(password)) {
            // Salva a clínica na sessão
            session.setAttribute("loggedClinic", clinic);
            return "redirect:/dashboard"; // redireciona para o GET /dashboard
        } else {
            model.addAttribute("error", "Credenciais inválidas");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Verifica se a clínica está logada
        Clinic clinic = (Clinic) session.getAttribute("loggedClinic");
        if (clinic == null) {
            return "redirect:/login";
        }
        // Envia informações da clínica para o template
        model.addAttribute("clinic", clinic);
        return "dashboard"; // nome do arquivo Thymeleaf (dashboard.html)
    }



}
