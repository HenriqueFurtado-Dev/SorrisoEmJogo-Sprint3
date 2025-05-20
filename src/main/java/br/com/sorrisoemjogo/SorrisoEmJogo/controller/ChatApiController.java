package br.com.sorrisoemjogo.SorrisoEmJogo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatApiController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @PostMapping("/ask")
    public String ask(@RequestBody String message) {
        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(userMessage);
        return chatClient.prompt(prompt)
                // .advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();
    }

    @PostMapping("/learn")
    public String learn(@RequestBody String message) {
        vectorStore.add(List.of(new Document(message)));
        return "Aprendi com isso";
    }
}
