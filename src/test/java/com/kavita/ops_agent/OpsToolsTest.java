package com.kavita.ops_agent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpsToolsTest {

    @Mock LogEntryRepository logs;
    @Mock IncidentRepository incidents;
    @InjectMocks OpsTools tools;

    @Test
    void searchLogsUsesKeywordForServiceAndMessage() {
        LogEntry entry = new LogEntry();
        entry.setMessage("timeout");
        when(logs.findTop10ByServiceContainingIgnoreCaseOrMessageContainingIgnoreCaseOrderByTimestampDesc("timeout", "timeout"))
                .thenReturn(List.of(entry));

        List<LogEntry> result = tools.searchLogs("timeout");

        assertEquals(1, result.size());
        assertEquals("timeout", result.get(0).getMessage());
    }

    @Test
    void getIncidentReturnsNullWhenMissing() {
        when(incidents.findById(99L)).thenReturn(Optional.empty());

        assertNull(tools.getIncident(99L));
    }

    @Test
    void listOpenIncidentsExcludesResolved() {
        Incident open = new Incident();
        open.setStatus("OPEN");
        Incident resolved = new Incident();
        resolved.setStatus("RESOLVED");
        when(incidents.findAll()).thenReturn(List.of(open, resolved));

        List<Incident> result = tools.listOpenIncidents();

        assertEquals(1, result.size());
        assertEquals("OPEN", result.get(0).getStatus());
    }
}