package com.kavita.ops_agent;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OpsTools {

    private final LogEntryRepository logs;
    private final IncidentRepository incidents;

    public OpsTools(LogEntryRepository logs, IncidentRepository incidents) {
        this.logs = logs;
        this.incidents = incidents;
    }

    @Tool(description = "Search recent application logs by service name or error text, for example 'checkout-service' or 'timeout'")
    public List<LogEntry> searchLogs(@ToolParam(description = "service name or keyword to search for") String keyword) {
        return logs.findTop10ByServiceContainingIgnoreCaseOrMessageContainingIgnoreCaseOrderByTimestampDesc(keyword, keyword);
    }

    @Tool(description = "Get one incident by its numeric id")
    public Incident getIncident(@ToolParam(description = "the incident id") Long id) {
        return incidents.findById(id).orElse(null);
    }

    @Tool(description = "List all incidents that are currently OPEN")
    public List<Incident> listOpenIncidents() {
        return incidents.findAll().stream()
                .filter(i -> "OPEN".equals(i.getStatus()))
                .toList();
    }
}