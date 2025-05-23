package br.com.sorrisoemjogo.SorrisoEmJogo.repository;

import br.com.sorrisoemjogo.SorrisoEmJogo.model.MessageLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageLogRepository extends MongoRepository<MessageLog, String> {
    List<MessageLog> findAllByOrderByCreatedAtDesc();
}