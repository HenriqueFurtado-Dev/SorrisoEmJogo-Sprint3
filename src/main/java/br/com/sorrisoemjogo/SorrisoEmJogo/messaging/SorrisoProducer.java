package br.com.sorrisoemjogo.SorrisoEmJogo.messaging;

import br.com.sorrisoemjogo.SorrisoEmJogo.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class SorrisoProducer {

    private final RabbitTemplate template;

    public SorrisoProducer(RabbitTemplate template) {
        this.template = template;
    }

    public void sendMessage(String message) {
        template.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                message
        );
    }
}
