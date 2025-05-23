package br.com.sorrisoemjogo.SorrisoEmJogo.messaging;

import br.com.sorrisoemjogo.SorrisoEmJogo.config.RabbitConfig;
import br.com.sorrisoemjogo.SorrisoEmJogo.model.MessageLog;
import br.com.sorrisoemjogo.SorrisoEmJogo.repository.MessageLogRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SorrisoConsumer {

    private final MessageLogRepository repo;

    public SorrisoConsumer(MessageLogRepository repo) {
        this.repo = repo;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receive(String msg) {
        System.out.println("RabbitMQ ► " + msg);
        repo.save(new MessageLog(msg));
    }
}
