package br.com.sorrisoemjogo.SorrisoEmJogo.controller;

import br.com.sorrisoemjogo.SorrisoEmJogo.messaging.SorrisoProducer;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.MessageLog;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.MessageLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mq")
@RequiredArgsConstructor
public class MessagingApi {

    private final SorrisoProducer producer;
    private final MessageLogRepository repo;

    @PostMapping("/send")
    public void send(@RequestBody String text) {
        producer.sendMessage(text);
    }

    @GetMapping("/history")
    public List<MessageLog> history() {
        return repo.findAllByOrderByCreatedAtDesc();
    }
}
