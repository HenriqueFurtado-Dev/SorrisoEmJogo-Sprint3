package br.com.sorrisoemjogo.SorrisoEmJogo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document("messages")
public class MessageLog {

    @Id
    private String id;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdAt;

    public MessageLog(String content) {
        this.content   = content;
        this.createdAt = LocalDateTime.now();
    }
}