package br.com.sorrisoemjogo.SorrisoEmJogo.controller;

import br.com.sorrisoemjogo.SorrisoEmJogo.messaging.SorrisoProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final SorrisoProducer producer;

    public TestController(SorrisoProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/mq-test")
    public String test() {
        producer.sendMessage("Olá, Rabbit!");
        return "Mensagem enviada!";
    }
}
