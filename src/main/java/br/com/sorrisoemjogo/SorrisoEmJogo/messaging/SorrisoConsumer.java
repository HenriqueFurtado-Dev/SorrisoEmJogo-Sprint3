package br.com.sorrisoemjogo.SorrisoEmJogo.messaging;

import br.com.sorrisoemjogo.SorrisoEmJogo.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SorrisoConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receive(String message) {
        System.out.println("Mensagem recebida do RabbitMQ: " + message);
    }
}
