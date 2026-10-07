package com.kavita.ops_agent;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findTop10ByMessageContainingIgnoreCaseOrderByTimestampDesc(String keyword);
}