package com.kavita.ops_agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("You are an SRE assistant. Be concise and factual.")
                .build();
    }

    @GetMapping
    public String chat(@RequestParam String message) {
        return chatClient.prompt().user(message).call().content();
    }
}