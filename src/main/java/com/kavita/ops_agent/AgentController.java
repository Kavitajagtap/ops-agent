package com.kavita.ops_agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final ChatClient chatClient;
    private final OpsTools opsTools;

    public AgentController(ChatClient.Builder builder, OpsTools opsTools) {
        this.opsTools = opsTools;
        this.chatClient = builder
                .defaultSystem("""
                        You are an SRE assistant that investigates incidents.
                        Always call the tools to look at real incident and log data before answering.
                        Never invent log lines or incident details.
                        If the tools return nothing relevant, say so.
                        Finish with a short probable cause and a suggested next step.
                        """)
                .build();
    }

    public record Question(String question) {}

    @PostMapping("/investigate")
    public String investigate(@RequestBody Question body) {
        return chatClient.prompt()
                .user(body.question())
                .tools(opsTools)
                .call()
                .content();
    }
}