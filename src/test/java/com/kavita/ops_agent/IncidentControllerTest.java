package com.kavita.ops_agent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncidentControllerTest {

    @Mock
    IncidentRepository repo;

    @InjectMocks
    IncidentController controller;

    @Test
    void oneReturnsIncidentWhenFound() {
        Incident incident = new Incident();
        incident.setTitle("Checkout down");
        when(repo.findById(1L)).thenReturn(Optional.of(incident));

        ResponseEntity<Incident> response = controller.one(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Checkout down", response.getBody().getTitle());
    }

    @Test
    void oneReturns404WhenMissing() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Incident> response = controller.one(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createIgnoresClientSuppliedId() {
        Incident incident = new Incident();
        incident.setId(42L);
        incident.setTitle("New incident");
        when(repo.save(any(Incident.class))).thenAnswer(inv -> inv.getArgument(0));

        Incident saved = controller.create(incident);

        assertNull(saved.getId());
        verify(repo).save(incident);
    }

    @Test
    void deleteMissingIncidentReturns404AndDeletesNothing() {
        when(repo.existsById(99L)).thenReturn(false);

        ResponseEntity<Void> response = controller.delete(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(repo, never()).deleteById(any());
    }
}