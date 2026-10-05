package com.kavita.ops_agent;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {
    private final IncidentRepository repo;

    public IncidentController(IncidentRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Incident> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Incident create(@Valid @RequestBody Incident incident) {
        incident.setId(null);
        return repo.save(incident);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Incident> update(@PathVariable Long id, @Valid @RequestBody Incident in) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(in.getTitle());
            existing.setSeverity(in.getSeverity());
            existing.setStatus(in.getStatus());
            existing.setService(in.getService());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}