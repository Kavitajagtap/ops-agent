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

                Rules for your report:
                - Put only facts that appear in tool results into "evidence".
                - Put your reasoning into "probableCause" and say it is an inference.
                - Set "confidence" to LOW if the evidence does not directly show the cause.
                - Set "confidence" to HIGH only when a log line or incident states the cause.
                - Keep "suggestedFix" to one or two concrete steps.
                """)
                .build();
    }

    public record Question(String question) {}

    @PostMapping("/investigate")
    public InvestigationReport investigate(@RequestBody Question body) {
        return chatClient.prompt()
                .user(body.question())
                .tools(opsTools)
                .call()
                .entity(InvestigationReport.class);
    }
}