package br.com.sorrisoemjogo.SorrisoEmJogo.controller;

import br.com.sorrisoemjogo.SorrisoEmJogo.repository.MessageLogRepository;
import br.com.sorrisoemjogo.SorrisoEmJogo.security.ClinicUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/messages")
public class MessagingPageController {

    @GetMapping
    public String page(Model model,
                       @AuthenticationPrincipal ClinicUserDetails u) {

        // carrega histórico inicial
        model.addAttribute("messages",
                messageLogRepository.findAllByOrderByCreatedAtDesc());

        model.addAttribute("clinic", u.getClinic());
        return "messages";   // messages.html (próxima seção)
    }

    private final MessageLogRepository messageLogRepository;
    public MessagingPageController(MessageLogRepository r) {
        this.messageLogRepository = r;
    }
}
