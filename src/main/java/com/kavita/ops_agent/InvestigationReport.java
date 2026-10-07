package com.kavita.ops_agent;

import java.util.List;

public record InvestigationReport(
        String summary,
        List<String> evidence,        // only facts that appear in tool results
        String probableCause,         // clearly an inference
        String suggestedFix,
        String confidence             // LOW, MEDIUM or HIGH
) {}